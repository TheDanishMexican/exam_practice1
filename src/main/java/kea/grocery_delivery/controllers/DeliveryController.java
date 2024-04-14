package kea.grocery_delivery.controllers;

import kea.grocery_delivery.dtos.DeliveryWithProductOrderDto;
import kea.grocery_delivery.entities.Delivery;
import kea.grocery_delivery.entities.ProductOrder;
import kea.grocery_delivery.services.DeliveryService;
import kea.grocery_delivery.services.ProductOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;
    private final ProductOrderService productOrderService;

    public DeliveryController(DeliveryService deliveryService, ProductOrderService productOrderService) {
        this.deliveryService = deliveryService;
        this.productOrderService = productOrderService;
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

    @PostMapping("/orders")
    public ResponseEntity<Delivery> addOrderToDelivery(@RequestBody DeliveryWithProductOrderDto request) {
        Optional<ProductOrder> order = productOrderService.findOrderById(request.productOrderId());
        Optional<Delivery> delivery = deliveryService.findDeliveryById(request.deliveryId());
        if (order.isEmpty() || delivery.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            deliveryService.addProductOrderToDelivery(delivery.get() ,order.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(delivery.get());
        } catch (Exception exception) {
            throw new RuntimeException("Could not add order to delivery", exception);
        }
    }


}
