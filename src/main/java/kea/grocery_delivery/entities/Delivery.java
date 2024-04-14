package kea.grocery_delivery.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate deliveryDate;
    private String fromWarehouse; // TODO: In future replace with actual warehouse entity
    private String destination;

    @OneToMany
    private List<ProductOrder> productOrders;

    public String totalWeight() {
        double sum = 0;

        for (ProductOrder productOrder : productOrders) {
            sum += productOrder.getProduct().getWeightInGrams();
        }

        return "Total weight: " + sum + " grams";
    }

    public String totalPrice() {
        double sum = 0;

        for (ProductOrder productOrder : productOrders) {
            sum += productOrder.getProduct().getPrice() * productOrder.getQuantity();
        }

        return "Total price: " + sum + " DKK";
    }
}
