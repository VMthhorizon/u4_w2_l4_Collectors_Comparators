package vincenzomola.entities;

import vincenzomola.enums.Status;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private final long id;
    private final Status status;
    private final LocalDate orderDate;
    private final LocalDate deliveryDate;
    private final List<Product> products;
    private final Customer customer;


    public Order(long id, Status status, LocalDate orderDate, LocalDate deliveryDate, List<Product> products,
                 Customer customer) {
        this.id = id;
        this.status = status;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.products = products;
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double calculateTotal() {

        double total = 0;
        for (Product product : products) {
            total += product
                    .getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", products=" + products +
                ", customer=" + customer +
                '}';
    }
}
