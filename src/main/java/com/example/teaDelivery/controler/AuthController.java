package com.example.teaDelivery.controler;

import com.example.pr6c.viewmodel.user.UserProfileView;
import com.example.teaDelivery.dto.UserRegistrationDto;
import com.example.teaDelivery.models.entity.User;
import com.example.teaDelivery.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class AuthController {

    private AuthService authService;
    private static final Logger logger = LogManager.getLogger(Controller.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("userRegistrationDto")
    public UserRegistrationDto initForm() {
        return new UserRegistrationDto();
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request) {
        logger.info("Incoming Request: Method = {}, URI = {}, User = {}",
                request.getMethod(),
                request.getRequestURI());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegistrationDto userRegistrationDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);

            return "redirect:/users/register";
        }

        this.authService.register(userRegistrationDto);
        logger.info("Incoming Request: Method = {}, URI = {}, User = {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRequestURI());
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        logger.info("Incoming Request: Method = {}, URI = {}, User = {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRequestURI());
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model, HttpServletRequest request) {
        String username = principal.getName();
        User user = authService.getUser(username);

        UserProfileView userProfileView = new UserProfileView(
                username,
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getLoyaltyPoints()
        );

        model.addAttribute("user", userProfileView);
        logger.info("Incoming Request: Method = {}, URI = {}, User = {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRequestURI());
        return "profile";
    }
}
