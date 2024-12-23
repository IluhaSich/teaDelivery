package com.example.teaDelivery.controler;

import com.example.pr6c.controller.DiscountsController;
import com.example.pr6c.viewmodel.base.BaseViewModel;
import com.example.pr6c.viewmodel.discounts.AllDiscountsViewModel;
import com.example.pr6c.viewmodel.discounts.DiscountViewModel;
import com.example.teaDelivery.dto.DiscountDto;
import com.example.teaDelivery.dto.UserDto;
import com.example.teaDelivery.models.entity.Discount;
import com.example.teaDelivery.models.entity.redis.Basket;
import com.example.teaDelivery.repository.UserRepository;
import com.example.teaDelivery.service.BasketService;
import com.example.teaDelivery.service.UserDiscountService;
import com.example.teaDelivery.service.DiscountService;
import com.example.teaDelivery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/discounts")
public class DiscountsControllerImpl implements DiscountsController {
    private final DiscountService discountService;
    private final UserDiscountService userDiscountService;
    private final UserService userService;
    private final BasketService basketService;
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(Controller.class);

    public DiscountsControllerImpl(DiscountService discountService, UserDiscountService userDiscountService, UserService userService, BasketService basketService, UserRepository userRepository) {
        this.discountService = discountService;
        this.userDiscountService = userDiscountService;
        this.userService = userService;
        this.basketService = basketService;
        this.userRepository = userRepository;
    }
    @GetMapping("/")
    @Override
    public String getDiscounts(Model model, Principal principal, HttpServletRequest request) {
        UserDto user = userService.getUserByName(principal.getName());
        List<DiscountDto> personalDiscounts = discountService.getAllDiscounts();
        List<DiscountViewModel> discountViewModels = personalDiscounts.stream().map(q ->
                new DiscountViewModel(
                        q.getDiscountName(),
                        q.getDescription(),
                        q.getTeaSort(),
                        q.getDiscount(),
                        q.getLoyalCost()
                )).toList();


        AllDiscountsViewModel allDiscountsViewModel = new AllDiscountsViewModel(
                new BaseViewModel("", ""),
                user.getLoyaltyPoints(),
                discountViewModels,
                userDiscountService.getUserDiscountByUserViewModel(user.getId())
        );
        model.addAttribute("model", allDiscountsViewModel);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "discounts";
    }
    @PostMapping("/apply-discount")
    @Override
    public String applyDiscount(@RequestParam String discountName, Principal principal, HttpServletRequest request) {
        String userId = principal.getName();
        Basket basket = basketService.getBasket(userId);
        if (basket == null) {
            return "redirect:/tea/";
        }
        Discount discount = discountService.getDiscountByName(discountName);

        if (discount != null) {
            basketService.applyDiscount(basket, discount);
        }
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "redirect:/basket/";
    }
    @PostMapping("/add-discount")
    @Override
    public String addDiscount(@RequestParam String discountName, Principal principal, HttpServletRequest request) {
        Discount discount = discountService.getDiscountByName(discountName);
        userDiscountService.addDiscount(discount, principal);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "redirect:/discounts/";
    }
}
