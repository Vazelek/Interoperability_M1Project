package hr.algreba.valentinherve.productapp.repository;

import hr.algreba.valentinherve.productapp.domain.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class MockProductRepository implements ProductRepository {

    private final Set<Product> MOCKED_PRODUCT = Stream.of(
            new Product(1L, "Frying pan", 2412, BigDecimal.valueOf(18.00)),
            new Product(2L, "Playstation 5", 3, BigDecimal.valueOf(550.00)),
            new Product(3L, "Pencil", 32001, BigDecimal.valueOf(1.50)),
            new Product(4L, "Rainbow", 178, BigDecimal.valueOf(16452.03)),
            new Product(5L, "Lemon", 18, BigDecimal.valueOf(3.20))
    ).collect(Collectors.toSet());


    @Override
    public Set<Product> findAll() {
        return MOCKED_PRODUCT;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return MOCKED_PRODUCT.stream().filter(product -> Objects.equals(product.getId(), id)).findAny();
    }

    @Override
    public Optional<Product> save(Product product) {
        if(!MOCKED_PRODUCT.contains(product)) {
            MOCKED_PRODUCT.add(product);
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> update(Long id, Product updatedProduct) {
        boolean exists = MOCKED_PRODUCT.removeIf(
                product -> Objects.equals(product.getId(), id) && Objects.equals(product.getId(), updatedProduct.getId())
        );

        if(exists) {
            MOCKED_PRODUCT.add(updatedProduct);
            return Optional.of(updatedProduct);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        MOCKED_PRODUCT.removeIf(product -> Objects.equals(product.getId(), id));
    }
}
