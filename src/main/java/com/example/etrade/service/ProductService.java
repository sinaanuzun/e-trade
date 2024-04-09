package com.example.etrade.service;

import com.example.etrade.model.Product;
import com.example.etrade.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long productId){
        return productRepository.findById(productId).orElse(null);
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Product updateProduct){
        return productRepository.save(updateProduct);
    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID bulunamadı : " + id));
        productRepository.delete(product);
        log.info("Product {} başarıyla silindi", product.getId() + " " + product.getProductName());
    }


}
