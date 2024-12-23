package com.example.teaDelivery.service;

import com.example.pr6c.viewmodel.discounts.PersonalDiscountViewModel;
import com.example.teaDelivery.dto.DiscountDto;
import com.example.teaDelivery.dto.UserDiscountDto;
import com.example.teaDelivery.models.entity.Discount;
import com.example.teaDelivery.models.entity.User;
import com.example.teaDelivery.models.entity.UserDiscount;
import com.example.teaDelivery.repository.UserDiscountRepository;
import com.example.teaDelivery.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDiscountService implements BaseService<UserDiscountDto, UserDiscount> {
    private final UserDiscountRepository userDiscountRepository;
    private final UserRepository userRepository;

    public UserDiscountService(UserDiscountRepository userDiscountRepository, UserRepository userRepository) {
        this.userDiscountRepository = userDiscountRepository;
        this.userRepository = userRepository;
    }

    public List<UserDiscountDto> getAllUserDiscounts() {
        return userDiscountRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<PersonalDiscountViewModel> getUserDiscountByUserViewModel(Long id) {
        List<UserDiscount> userDiscount = getUserDiscountByUser(id);
        List<Discount> discounts = userDiscount.stream().map(UserDiscount::getPersonalDiscount).toList();

        List<DiscountDto> discountDtos = discounts.stream().map(q -> new DiscountDto(
                q.getId(),
                q.getDiscountName(),
                q.getDescription(),
                q.getTeaSort(),
                q.getDiscount(),
                q.getLoyalCost()
        )).toList();
        List<PersonalDiscountViewModel> personalDiscountViewModels =
                discountDtos.stream().map(q -> new PersonalDiscountViewModel(
                        q.getId(),
                        q.getDiscountName(),
                        q.getDescription(),
                        q.getTeaSort(),
                        q.getDiscount()
                )).toList();
        return personalDiscountViewModels;
    }

    public List<UserDiscount> getUserDiscountByUser(Long id) {
        return userDiscountRepository.findByUserId(id);
    }

    @Override
    public UserDiscountDto convertToDto(UserDiscount userDiscount) {
        return new UserDiscountDto(
                userDiscount.getId(),
                userDiscount.getId(),
                userDiscount.getUser().getId(),
                userDiscount.isWasUsed()
        );
    }

    public void addDiscount(Discount discount, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        if (user.getLoyaltyPoints() < 100){return;}
        UserDiscount userDiscount = new UserDiscount(discount, userRepository.findByUsername(principal.getName()).orElseThrow(), false);
        user.setLoyaltyPoints(user.getLoyaltyPoints() - 100);
        userRepository.save(user);
        userDiscountRepository.save(userDiscount);
    }
}
