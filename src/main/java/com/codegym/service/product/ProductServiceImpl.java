package com.codegym.service.product;

import com.codegym.model.Product;
import com.codegym.repository.ProductRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        products.forEach(product -> Hibernate.initialize(product.getImages()));
        return products;
    }

    @Override
    public Product findById(Long id) {
        Product product = productRepository.findOne(id);
        Hibernate.initialize(product.getImages());
        return product;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
        productRepository.delete(id);
    }
}
