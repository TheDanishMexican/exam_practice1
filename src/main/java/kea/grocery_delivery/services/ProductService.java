package kea.grocery_delivery.services;

import kea.grocery_delivery.dtos.ProductDto;
import kea.grocery_delivery.entities.Product;
import kea.grocery_delivery.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id).map(this::toDto).orElse(null);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::toDto).toList();
    }

    public ProductDto getByName(String name) {
        return toDto(productRepository.findByName(name));
    }

    public ResponseEntity deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ProductDto addProduct(ProductDto request) {
        if (request.id() != null) {
            throw new IllegalArgumentException("Product ID must not be provided when adding a new product");
        }

        Product newProduct = new Product();
        updateProduct(newProduct, request);
        productRepository.save(newProduct);

        return toDto(newProduct);
    }

    public ProductDto editProduct(Long id, ProductDto request) {
        if (request.id() != id) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot change the id of an existing product");
        }
        Product productToEdit = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        updateProduct(productToEdit, request);
        productRepository.save(productToEdit);
        return toDto(productToEdit);
    }

    public void updateProduct(Product original, ProductDto request) {
        original.setName(request.name());
        original.setPrice(request.price());
        original.setWeightInGrams(request.weightInGrams());
    }

    public ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getWeightInGrams());
    }
}
