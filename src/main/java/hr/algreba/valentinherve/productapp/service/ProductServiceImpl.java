package hr.algreba.valentinherve.productapp.service;
import hr.algreba.valentinherve.productapp.domain.Product;
import hr.algreba.valentinherve.productapp.dto.ProductDTO;
import hr.algreba.valentinherve.productapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id).map(ProductDTO::new);
    }

    @Override
    public Optional<ProductDTO> save(Product product) {
        return productRepository
                .save(new Product(product))
                .map(ProductDTO::new);
    }

    @Override
    public Optional<ProductDTO> update(Long id, Product updateProduct) {
        return productRepository
                .update(id, new Product(updateProduct))
                .map(ProductDTO::new);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
