package carApplication.service;

import carApplication.model.Car;
import carApplication.model.CarOrder;
import carApplication.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public CarOrder placeOrder(Car car, String customerName) {
        CarOrder order = new CarOrder(car, customerName);
        order.confirmOrder();

        // Save to database instead of ArrayList
        return orderRepository.save(order);
    }

    public List<CarOrder> getAllOrders() {
        return orderRepository.findAll(); //
    }

    public Optional<CarOrder> getOrderById(String orderId) {
        return orderRepository.findById(orderId); //
    }
}