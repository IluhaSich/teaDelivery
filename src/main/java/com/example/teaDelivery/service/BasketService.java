package com.example.teaDelivery.service;

import com.example.teaDelivery.models.entity.Discount;
import com.example.teaDelivery.models.entity.redis.Basket;
import com.example.teaDelivery.models.entity.redis.TeaItem;
import com.example.teaDelivery.repository.TeaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasketService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String BASKET_PREFIX = "basket:";

    public BasketService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Basket getBasket(String userId) {
        return (Basket) redisTemplate.opsForValue().get(BASKET_PREFIX + userId);
    }

    public void saveBasket(String userId, Basket basket) {
        redisTemplate.opsForValue().set(BASKET_PREFIX + userId, basket);
    }

    public void addTeaToBasket(String userId, TeaItem teaItem) {
        Basket basket = getBasket(userId);
        if (basket == null) {
            basket = new Basket();
            basket.setUserId(userId);
        }
        Optional<TeaItem> existItem = basket.getTeas().stream().filter(item -> item.getTeaId().equals(teaItem.getTeaId())).findFirst();
        if (existItem.isEmpty()) {
            teaItem.setDiscountedCost(teaItem.getCost());
            basket.getTeas().add(teaItem);
        } else {
            existItem.get().setQuantity(existItem.get().getQuantity() + 1);
        }
        updateBasketCosts(basket);
        saveBasket(userId, basket);
    }

    public void decrementTeaQuantity(String userId, Long teaId) {
        Basket basket = getBasket(userId);
        if (basket != null) {
            Optional<TeaItem> itemOptional = basket.getTeas().stream()
                    .filter(tea -> tea.getTeaId().equals(teaId))
                    .findFirst();

            if (itemOptional.isPresent()) {
                TeaItem item = itemOptional.get();
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    basket.getTeas().remove(item);
                }
                updateBasketCosts(basket);
                saveBasket(userId, basket);
            }
        }
    }

    public void clearBasket(String userId) {
        redisTemplate.delete(BASKET_PREFIX + userId);
    }

    public void applyDiscount(Basket basket, Discount discount) {
        for (TeaItem item : basket.getTeas()) {
            if (item.getSort().equalsIgnoreCase(discount.getTeaSort())) {
                double discountedPrice = item.getCost() * (1 - discount.getDiscount());
                item.setDiscountedCost(discountedPrice);
            } else {
                item.setDiscountedCost(item.getCost());
            }
        }
        recalculateBasket(basket);
        saveBasket(basket.getUserId(), basket);
    }

    private void recalculateBasket(Basket basket) {
        double totalCost = 0;
        double discountedCost = 0;

        for (TeaItem item : basket.getTeas()) {
            totalCost += item.getCost() * item.getQuantity();
            discountedCost += item.getDiscountedCost() * item.getQuantity();
        }

        basket.setTotalCost(totalCost);
        basket.setDiscountedCost(discountedCost);
    }

    private void updateBasketCosts(Basket basket) {
        double totalCost = basket.getTeas().stream()
                .mapToDouble(tea -> tea.getCost() * tea.getQuantity()).sum();
        double discountedCost = basket.getTeas().stream()
                .mapToDouble(tea -> tea.getDiscountedCost() * tea.getQuantity()).sum();
        basket.setTotalCost(totalCost);
        basket.setDiscountedCost(discountedCost);

    }

    public double getBasketCost(String userId) {
        Basket basket = getBasket(userId);
        if (basket != null && !basket.getTeas().isEmpty()) {
            double totalCost = basket.getTeas().stream()
                    .mapToDouble(tea -> tea.getCost() * tea.getQuantity()).sum();
            double discountedCost = basket.getTeas().stream()
                    .mapToDouble(tea -> tea.getDiscountedCost() * tea.getQuantity()).sum();
            basket.setTotalCost(totalCost);
            basket.setDiscountedCost(discountedCost);
        }
        return basket.getDiscountedCost();
    }
}


