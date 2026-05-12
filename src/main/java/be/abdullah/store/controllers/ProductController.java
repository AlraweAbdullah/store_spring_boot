package be.abdullah.store.controllers;

import be.abdullah.store.dtos.ProductDto;
import be.abdullah.store.dtos.RegisterProductRequest;
import be.abdullah.store.dtos.UpdateProductRequest;
import be.abdullah.store.exceptions.CategoryNotfoundException;
import be.abdullah.store.exceptions.ProductNotFoundException;
import be.abdullah.store.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products")
public class ProductController {
    private final ProductService productService;


    @GetMapping
    public List<ProductDto> getProducts(@RequestParam(required = false) Byte categoryId) {
        return productService.getProducts(categoryId);
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody RegisterProductRequest request, UriComponentsBuilder uriBuilder) {
        var productDto = productService.createProduct(request);

        var uri = uriBuilder.path("/api/v1/products/{productId}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{productId}")
    public ProductDto updateProduct(@PathVariable Long productId, @Valid @RequestBody UpdateProductRequest request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CategoryNotfoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Category not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found"));
    }
}
