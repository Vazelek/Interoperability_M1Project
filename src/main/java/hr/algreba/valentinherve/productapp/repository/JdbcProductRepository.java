package hr.algreba.valentinherve.productapp.repository;

import hr.algreba.valentinherve.productapp.domain.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Primary
@Repository
public class JdbcProductRepository implements ProductRepository {

    private static final String TABLE_NAME = "product";
    private static final String GENERATED_KEY_COLUMN = "id";

    private static final String SELECT_ALL = "SELECT * from product";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(GENERATED_KEY_COLUMN);
    }


    @Override
    public Set<Product> findAll() {
        return Set.copyOf(jdbcTemplate.query(SELECT_ALL, this::mapRowToProduct));
    }

    @Override
    public Optional<Product> findById(Long id) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_ALL + " WHERE id = ?", this::mapRowToProduct, id)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> save(Product product) {
        try {
            product.setId(saveProductDetails(product));
            return Optional.of(product);
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> update(Long id, Product updatedProduct) {
        int executed = jdbcTemplate.update("UPDATE product set " +
                        "name = ?, " +
                        "stock = ?, " +
                        "price = ? " +
                    "WHERE id = ?",
                updatedProduct.getName(),
                updatedProduct.getStock(),
                updatedProduct.getPrice(),
                id
        );

        return executed > 0 ? Optional.of(updatedProduct) : Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }

    private Product mapRowToProduct(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("stock"),
                rs.getBigDecimal("price")
        );
    }

    private long saveProductDetails(Product product) {
        Map<String, Object> values = new HashMap<>();

        values.put("name", product.getName());
        values.put("stock", product.getStock());
        values.put("price", product.getPrice());

        return simpleJdbcInsert.executeAndReturnKey(values).longValue();
    }

}
