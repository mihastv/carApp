package carApplication.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class CarOrder {
    @Id
    private String orderId;

    private String customerName;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL) // Saving order saves the car
    @JoinColumn(name = "car_vin")
    private Car car;

    public enum OrderStatus {
        PENDING, CONFIRMED, CANCELLED
    }

    protected CarOrder() {}

    public CarOrder(Car car, String customerName) {
        this.orderId = "ORD-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.car = car;
        this.customerName = customerName;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public String getOrderId() { return orderId; }
    public Car getCar() { return car; }
    public String getCustomerName() { return customerName; }
    public java.time.LocalDateTime getOrderDate() { return orderDate; }
    public OrderStatus getStatus() { return status; }

    public void confirmOrder() {
        if (status == OrderStatus.PENDING) {
            status = OrderStatus.CONFIRMED;
        }
    }

    public void cancelOrder() {
        if (status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED) {
            status = OrderStatus.CANCELLED;
        }
    }

    public String getOrderSummary() {
        StringBuilder sb = new StringBuilder();
        String separator = "═".repeat(60);

        sb.append("\n").append(separator).append("\n");
        sb.append("  ORDER CONFIRMATION\n");
        sb.append(separator).append("\n\n");

        sb.append(String.format("  Order ID: %s\n", orderId));
        sb.append(String.format("  Customer: %s\n", customerName));
        sb.append(String.format("  Order Date: %s\n", orderDate.format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        sb.append(String.format("  Status: %s\n", status));

        sb.append(car.getFullSpecification());

        return sb.toString();
    }
}