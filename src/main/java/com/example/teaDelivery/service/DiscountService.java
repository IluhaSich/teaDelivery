package com.example.teaDelivery.service;

import com.example.pr6c.viewmodel.discounts.PersonalDiscountViewModel;
import com.example.teaDelivery.dto.DiscountDto;
import com.example.teaDelivery.models.entity.Discount;
import com.example.teaDelivery.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService implements BaseService<DiscountDto, Discount>{
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<DiscountDto> getAllDiscounts(){
        return discountRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public DiscountDto convertToDto(Discount discount) {
        return new DiscountDto(
                discount.getId(),
                discount.getDiscountName(),
                discount.getDescription(),
                discount.getTeaSort(),
                discount.getDiscount(),
                discount.getLoyalCost());
    }

    public void saveDiscount(Discount discount) {
    }

    public Discount getDiscountByName(String discountName) {
        return discountRepository.getByDiscountName(discountName);
    }
}
