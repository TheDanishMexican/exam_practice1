package kea.grocery_delivery.controllers;

import kea.grocery_delivery.entities.Delivery;
import kea.grocery_delivery.services.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery request) {
        if (request.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            Delivery newDelivery = deliveryService.createDelivery(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDelivery);
        } catch (Exception exception) {
            throw new RuntimeException("Could not create delivery", exception);
        }
    }



}
