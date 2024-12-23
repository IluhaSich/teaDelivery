package com.example.teaDelivery.controler;

import com.example.pr6c.controller.BasketController;
import com.example.pr6c.viewmodel.base.BaseViewModel;
import com.example.pr6c.viewmodel.basket.BasketItemViewModel;
import com.example.pr6c.viewmodel.basket.BasketViewModel;
import com.example.teaDelivery.dto.TeaDto;
import com.example.teaDelivery.models.entity.Tea;
import com.example.teaDelivery.models.entity.TeaInOrder;
import com.example.teaDelivery.models.entity.TeaOrder;
import com.example.teaDelivery.models.entity.User;
import com.example.teaDelivery.models.entity.redis.Basket;
import com.example.teaDelivery.models.entity.redis.TeaItem;
import com.example.teaDelivery.models.enums.OrderStatus;
import com.example.teaDelivery.repository.*;
import com.example.teaDelivery.service.BasketService;
import com.example.teaDelivery.service.TeaOrderService;
import com.example.teaDelivery.service.TeaService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketControllerImpl implements BasketController {
    private final BasketService basketService;
    private final TeaService teaService;
    private final UserRepository userRepository;
    private final TeaOrderRepository teaOrderRepository;
    private final TeaInOrderRepository teaInOrderRepository;
    private final TeaOrderService teaOrderService;
    private final TeaRepository teaRepository;
    private final DiscountRepository discountRepository;
    private static final Logger logger = LogManager.getLogger(Controller.class);

    public BasketControllerImpl(BasketService basketService, TeaService teaService, UserRepository userRepository,
                                TeaOrderRepository teaOrderRepository, TeaInOrderRepository teaInOrderRepository, TeaOrderService teaOrderService, TeaRepository teaRepository, DiscountRepository discountRepository) {
        this.basketService = basketService;
        this.teaService = teaService;
        this.userRepository = userRepository;
        this.teaOrderRepository = teaOrderRepository;
        this.teaInOrderRepository = teaInOrderRepository;
        this.teaOrderService = teaOrderService;
        this.teaRepository = teaRepository;
        this.discountRepository = discountRepository;
    }
    @GetMapping("/")
    @Override
    public String getBasket(Model model, HttpServletRequest request) {
        String userId = getCurrentUserId();
        Basket basket = basketService.getBasket(userId);
        BasketViewModel basketViewModel;
        if (basket == null) {
            basketViewModel = new BasketViewModel(
                    new BaseViewModel("", ""),
                    0,
                    0,
                    null);
        } else {
            List<BasketItemViewModel> basketItemViewModel =
                    basket.getTeas().stream().map(q ->
                            new BasketItemViewModel(
                                    q.getTeaId(),
                                    q.getSort(),
                                    q.getName(),
                                    q.getCost(),
                                    q.getQuantity())).toList();
            basketViewModel = new BasketViewModel(
                    new BaseViewModel("", ""),
                    basket.getTotalCost(),
                    basket.getDiscountedCost(),
                    basketItemViewModel);
        }
        model.addAttribute("model", basketViewModel);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "basket";
    }
    @PostMapping("/order")
    @Override
    public String order(Principal principal,
                        @RequestParam String deliveryCity,
                        @RequestParam String deliveryState,
                        @RequestParam String deliveryStreet,
                        @RequestParam String deliveryZip,
                        HttpServletRequest request) {
        String userId = getCurrentUserId();
        Basket basket = basketService.getBasket(userId);
        if (basket == null || basket.getTeas().isEmpty()){
            return "redirect:/tea/";
        }
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        TeaOrder teaOrder = new TeaOrder(
                user, LocalDateTime.now(), discountRepository.getByDiscountName("Green friday"),
                basketService.getBasketCost(userId), LocalDateTime.now(),
                deliveryCity, deliveryState, deliveryStreet, deliveryZip, OrderStatus.ORDERED);
        teaOrderRepository.save(teaOrder);

        List<TeaItem> teas = basket.getTeas();
        for (TeaItem teaItem : teas) {
            while (teaItem.getQuantity() > 0) {
                Tea tea = teaRepository.getById(teaItem.getTeaId()).orElseThrow();
                teaInOrderRepository.save(new TeaInOrder(teaOrder, tea));
                teaItem.setQuantity(teaItem.getQuantity() - 1);
            }
        }
        basketService.clearBasket(userId);
        user.setLoyaltyPoints(user.getLoyaltyPoints() + 100);
        userRepository.save(user);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "redirect:/tea/";
    }
    @PostMapping("/clear")
    @Override
    public String clearBasket(HttpServletRequest request) {
        String userId = getCurrentUserId();
        basketService.clearBasket(userId);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "redirect:/tea/";
    }
    @PostMapping("/add-tea")
    @Override
    public String addTeaToBasket(@RequestParam Long teaId, HttpServletRequest request) {
        String userId = getCurrentUserId();
        TeaDto tea = teaService.getTeaById(teaId);
        TeaItem teaItem = new TeaItem(tea.getId(), tea.getSort(), tea.getName(), tea.getCost(), tea.getCost(), 1);
        basketService.addTeaToBasket(userId, teaItem);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "redirect:/basket/";
    }
    @PostMapping("/remove-tea")
    @Override
    public String removeTeaFromBasket(@RequestParam Long teaId, HttpServletRequest request) {
        String userId = getCurrentUserId();
        basketService.decrementTeaQuantity(userId, teaId);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "redirect:/basket/";
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}