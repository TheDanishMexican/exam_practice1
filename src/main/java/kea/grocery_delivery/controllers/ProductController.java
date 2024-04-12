package kea.grocery_delivery.controllers;

import kea.grocery_delivery.dtos.ProductDto;
import kea.grocery_delivery.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    public ProductDto getByName(@PathVariable String name) {
        return productService.getByName(name);
    }

    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    public ProductDto addProduct(ProductDto request) {
        return productService.addProduct(request);
    }

    public void updateProduct(@PathVariable Long id, ProductDto request) {
        productService.editProduct(id, request);
    }
}
