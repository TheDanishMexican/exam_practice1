package kea.grocery_delivery.repositories;

import kea.grocery_delivery.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findByName(String name);
}
