package kea.grocery_delivery.controllers;

import kea.grocery_delivery.dtos.ProductDto;
import kea.grocery_delivery.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Optional<ProductDto> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDto> getByName(@PathVariable String name) {
        Optional<ProductDto> optionalProduct = productService.getByName(name);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        if(products.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        Optional<ProductDto> optionalProduct = productService.getProductById(id);
        if (optionalProduct.isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto request) {
        try {
            ProductDto addedProduct = productService.addProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    public void updateProduct(@PathVariable Long id, ProductDto request) {
        productService.editProduct(id, request);
    }
}
