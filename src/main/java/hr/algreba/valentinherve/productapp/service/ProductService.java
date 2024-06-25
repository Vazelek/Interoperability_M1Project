package hr.algreba.valentinherve.productapp.service;

import hr.algreba.valentinherve.productapp.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAll();

    Optional<ProductDTO> findById(Long id);

    void deleteById(Long id);

}
