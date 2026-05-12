package be.abdullah.store.services;

import be.abdullah.store.dtos.ProductDto;
import be.abdullah.store.dtos.RegisterProductRequest;
import be.abdullah.store.dtos.UpdateProductRequest;
import be.abdullah.store.entities.Product;
import be.abdullah.store.exceptions.CategoryNotfoundException;
import be.abdullah.store.exceptions.ProductNotFoundException;
import be.abdullah.store.mappers.ProductMapper;
import be.abdullah.store.repositories.CategoryRepository;
import be.abdullah.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductMapper productMapper;

    public List<ProductDto> getProducts(Byte categoryId) {
        List<Product> products;

        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAll();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    public ProductDto getProduct(Long productId) {
        var savedProduct = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        return productMapper.toDto(savedProduct);
    }

    public ProductDto createProduct(RegisterProductRequest request) {
        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow(CategoryNotfoundException::new);
        var product = productMapper.toEntity(request);

        product.setCategory(category);
        var savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(Long productId, UpdateProductRequest request) {
        var product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        var category = categoryRepository.findById(request.getCategoryId()).orElseThrow(CategoryNotfoundException::new);

        productMapper.updateProduct(request, product);

        product.setCategory(category);
        var savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);

    }

    public void deleteProduct(Long productId) {
        var product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
    }

}
