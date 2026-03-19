package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;

@Service
@Transactional
@SuppressWarnings("null")
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAllAvailable() {
        return productRepository.findByAvailableTrue();
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        product.setCategory(category);
        product.setAvailable(true); // Por defecto disponible
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public Product update(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setAvailable(productDetails.isAvailable());
        
        // Si cambia de categoría
        if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
            Category category = categoryRepository.findById(productDetails.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            product.setCategory(category);
        }
        
        return productRepository.save(product);
    }

    public Optional<Product> partialUpdate(Long id, Product updates) {
        return productRepository.findById(id).map(product -> {
            if (updates.getName() != null) product.setName(updates.getName());
            if (updates.getDescription() != null) product.setDescription(updates.getDescription());
            if (updates.getPrice() != null) product.setPrice(updates.getPrice());
            if (updates.getStock() != null) product.setStock(updates.getStock());
            // El booleano se puede cambiar directamente ya que no es null
            product.setAvailable(updates.isAvailable());
            
            return productRepository.save(product);
        });
    }

    // Podructo no disponible
    public Product disableProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setAvailable(false);
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndAvailableTrue(categoryId);
    }
}
