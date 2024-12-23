package com.example.teaDelivery.controler;


import com.example.pr6c.controller.MainController;
import com.example.pr6c.viewmodel.base.BaseViewModel;
import com.example.pr6c.viewmodel.main.MainViewModel;
import com.example.pr6c.viewmodel.tea.PersonalDiscountViewModel;
import com.example.pr6c.viewmodel.tea.TeaViewModel;
import com.example.teaDelivery.dto.TeaDto;
import com.example.teaDelivery.service.IngredientService;
import com.example.teaDelivery.service.SupplierService;
import com.example.teaDelivery.service.TeaService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainControllerImpl implements MainController {
    TeaService teaService;
    IngredientService ingredientService;
    SupplierService supplierService;
    private static final Logger logger = LogManager.getLogger(Controller.class);


    List<PersonalDiscountViewModel> personalDiscountViewModels = new ArrayList<>(List.of());

    public MainControllerImpl(TeaService teaService, IngredientService ingredientService, SupplierService supplierService) {
        this.teaService = teaService;
        this.ingredientService = ingredientService;
        this.supplierService = supplierService;
        personalDiscountViewModels.add(new PersonalDiscountViewModel("Черная пятница", "black description", "black tea", 10, false));
        personalDiscountViewModels.add(new PersonalDiscountViewModel("Зеленая пятница", "green description", "green tea", 20, false));
    }

    @GetMapping("/")
    @Override
    public String GetMain(Model model, HttpServletRequest request) {
        TeaDto teaDto = teaService.getLastTea();
        BaseViewModel baseViewModel = new BaseViewModel("main","JohnDoe");
        TeaViewModel teaViewModel = new TeaViewModel(
                baseViewModel,
                teaDto.getId(),
                teaDto.getImage(),
                teaDto.getName(),
                teaDto.getDescription(),
                teaDto.getSort(),
                ingredientService.getIngredientsByTeaId(teaDto.getId()),
                teaDto.getCost(),
                teaDto.isAvailability(),
                supplierService.getSupplierById(teaDto.getSuppliers()).getSupplier_name(),
                false
        );
        MainViewModel viewModel = new MainViewModel(
                teaViewModel,
                personalDiscountViewModels,
                teaService.getAllSorts()
        );
        model.addAttribute("model", viewModel);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "index";
    }
}
