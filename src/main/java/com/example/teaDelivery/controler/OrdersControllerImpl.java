package com.example.teaDelivery.controler;

import com.example.pr6c.controller.OrdersController;
import com.example.pr6c.viewmodel.base.BaseViewModel;
import com.example.pr6c.viewmodel.orders.AllOrdersViewModel;
import com.example.pr6c.viewmodel.orders.OrderViewModel;
import com.example.pr6c.viewmodel.orders.OrdersSearchForm;
import com.example.pr6c.viewmodel.tea.TeaViewModel;
import com.example.teaDelivery.dto.TeaOrderDto;
import com.example.teaDelivery.service.TeaInOrderService;
import com.example.teaDelivery.service.TeaOrderService;
import com.example.teaDelivery.service.TeaService;
import com.example.teaDelivery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrdersControllerImpl implements OrdersController{
    private final TeaOrderService teaOrderService;
    private final TeaInOrderService teaInOrderService;
    private final UserService userService;
    private final TeaService teaService;
    private static final Logger logger = LogManager.getLogger(Controller.class);

    public OrdersControllerImpl(TeaOrderService teaOrderService, TeaInOrderService teaInOrderService, UserService userService, TeaService teaService) {
        this.teaOrderService = teaOrderService;
        this.teaInOrderService = teaInOrderService;
        this.userService = userService;
        this.teaService = teaService;
    }

    @Override
    @GetMapping("/")
    public String getAllOrders(@ModelAttribute("form") OrdersSearchForm form, Model model, HttpServletRequest request) {
        String orderStatus = form.orderStatus() != null ? form.orderStatus() : "";
        Integer page = form.page() != null ? form.page() : 1;
        Integer size = form.size() != null ? form.size() : 5;
        form = new OrdersSearchForm(orderStatus, page, size);


        Page<TeaOrderDto> orderDto = teaOrderService.getAllOrders(page, size);
        List<OrderViewModel> orders = orderDto.stream().map(q -> new OrderViewModel(
                new BaseViewModel("",""),
                q.getId(),
                userService.getUserById(q.getClient()).getName(),
                1, //TODO: Стоимость всего заказа
                teaInOrderService.getTeasByOrderId(q.getId()),
                q.getTime(),
                q.getDeliveryCity() + ", " + q.getDeliveryState() + ", " + q.getDeliveryStreet() + ", " + q.getDeliveryZip(),
                q.getOrderStatus()

        )).toList();
        AllOrdersViewModel allOrdersViewModel = new AllOrdersViewModel(
                new BaseViewModel("", ""),
                orders,
                form,
                orderDto.getTotalPages()
        );
        model.addAttribute("model", allOrdersViewModel);
        model.addAttribute("form", form);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "order-list";
    }


    @Override
    @GetMapping("/{id}")
    public String getOrder(Model model, @PathVariable Long id, HttpServletRequest request) {
        TeaOrderDto teaOrderDto = teaOrderService.getOrderById(id);
        List<TeaViewModel> teasDto = teaInOrderService.getTeasByOrderId(id);


        OrderViewModel orderViewModel = new OrderViewModel(
                new BaseViewModel("",""),
                teaOrderDto.getId(),
                userService.getUserById(teaOrderDto.getId()).getName(),
                teaOrderDto.getCost(),
                teasDto,
                teaOrderDto.getTime(),
                teaOrderDto.getDeliveryCity() + ", "
                        + teaOrderDto.getDeliveryState() + ", "
                        + teaOrderDto.getDeliveryStreet() + ", "
                        + teaOrderDto.getDeliveryZip(),
                teaOrderDto.getOrderStatus()
        );
        model.addAttribute("model", orderViewModel);
        logger.info("Incoming Request: Method = {}, URI = {}",
                request.getMethod(),
                request.getRequestURI());
        return "order";
    }
}
