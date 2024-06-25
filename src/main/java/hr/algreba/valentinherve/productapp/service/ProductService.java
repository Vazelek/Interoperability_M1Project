package hr.algreba.valentinherve.productapp.service;

import hr.algreba.valentinherve.productapp.domain.Product;
import hr.algreba.valentinherve.productapp.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAll();

    Optional<ProductDTO> findById(Long id);

    Optional<ProductDTO> save(Product product);

    Optional<ProductDTO> update(Long id, Product updateProduct);

    void deleteById(Long id);

}
