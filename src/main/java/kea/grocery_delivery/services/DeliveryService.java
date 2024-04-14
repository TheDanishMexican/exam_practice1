package kea.grocery_delivery.services;

import kea.grocery_delivery.entities.Delivery;
import kea.grocery_delivery.repositories.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery createDelivery(Delivery request) {
        Delivery newDelivery = new Delivery();
        updateDelivery(newDelivery, request);
        deliveryRepository.save(newDelivery);

        return newDelivery;
    }

    private void updateDelivery(Delivery original, Delivery request) {
        original.setDeliveryDate(request.getDeliveryDate());
        original.setFromWarehouse(request.getFromWarehouse());
        original.setDestination(request.getDestination());
    }
}