package kea.grocery_delivery.services;

import kea.grocery_delivery.entities.Delivery;
import kea.grocery_delivery.entities.Van;
import kea.grocery_delivery.repositories.VanRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VanService {
    private final VanRepository vanRepository;
    private final DeliveryService deliveryService;

    public VanService(VanRepository vanRepository, DeliveryService deliveryService) {
        this.vanRepository = vanRepository;
        this.deliveryService = deliveryService;
    }

    public Optional<Van> findById(Long id) {
        return vanRepository.findById(id);
    }

    public void addDeliveryToVan(Van van, Delivery delivery) {
        if (van.remainingCapacity() < 0) {
            throw new IllegalArgumentException("Van is full");
        }

        System.out.println("Van remaining capacity: " + van.remainingCapacity() + " kg");

        van.getDeliveries().add(delivery);
        vanRepository.save(van);
    }
}
