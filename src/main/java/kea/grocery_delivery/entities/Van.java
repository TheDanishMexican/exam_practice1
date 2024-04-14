package kea.grocery_delivery.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Van {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int capacityInKg;

    @OneToMany
    private List<Delivery> deliveries;

    public int remainingCapacity() {
        int usedCapacity = 0;

        for (Delivery delivery : deliveries) {
            System.out.println("Delivery total weight = " + delivery.totalWeight() + " grams");
            double deliveryWeightInKg = delivery.totalWeight() / 1000.0; // Convert grams to kilograms
            usedCapacity += (int) deliveryWeightInKg;
        }

        return capacityInKg - usedCapacity;
    }
}
