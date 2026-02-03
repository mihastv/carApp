package carApplication.controller;

import carApplication.dto.CustomCarRequest;
import carApplication.model.*;
import carApplication.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class CarController {
    private final CarConfigurationDirector director;
    private final OrderService orderService;

    public CarController(CarConfigurationDirector director, OrderService orderService) {
        this.director = director;
        this.orderService = orderService;
    }

    @GetMapping
    public List<CarOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/luxury")
    public CarOrder orderLuxury(@RequestParam String model, @RequestParam String customer) {
        Car luxuryCar = director.buildLuxuryCar(model);
        return orderService.placeOrder(luxuryCar, customer);
    }

    @PostMapping("/custom")
    public CarOrder orderCustom(@RequestBody CustomCarRequest request) {
        CustomCarBuilder builder = new CustomCarBuilder();

        Car customCar = builder.reset()
                .setModel(request.getModel())
                .setEngine(request.getEngine())
                .setTransmission(request.getTransmission())
                .build();

        return orderService.placeOrder(customCar, request.getCustomerName());
    }
}