package hr.algreba.valentinherve.productapp.repository;

import hr.algreba.valentinherve.productapp.domain.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductRepository {

    Set<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> save(Product product);

    Optional<Product> update(Long id, Product updatedProduct);

    void deleteById(Long id);

}
