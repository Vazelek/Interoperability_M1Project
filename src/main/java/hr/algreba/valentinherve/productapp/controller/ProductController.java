package hr.algreba.valentinherve.productapp.controller;

import hr.algreba.valentinherve.productapp.domain.Product;
import hr.algreba.valentinherve.productapp.dto.ProductDTO;
import hr.algreba.valentinherve.productapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    private final JmsTemplate jmsTemplate;

    public ProductController(ProductService productService, JmsTemplate jmsTemplate) {
        this.productService = productService;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        jmsTemplate.convertAndSend("Getting all products from the database");
        return productService.findAll();
    }

    @GetMapping("{id}")
    public ProductDTO getById(@PathVariable final Long id) {
        jmsTemplate.convertAndSend("Getting products with id " + id + " from the database");
        return productService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product was not found by that id")
                );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDTO save(@RequestBody Product product){
        jmsTemplate.convertAndSend("Saving product to the database: " + product.getName());
        return productService.save(product)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Product with the same id already exists"));
    }

    @PutMapping("{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody final Product updatedProduct){
        jmsTemplate.convertAndSend("Updating product with id " + id + " from the database: " + updatedProduct.getName());
        return productService.update(id, updatedProduct)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product was not found by that id")
                );
    }

    @DeleteMapping("{id}")
    public ProductDTO delete(@PathVariable Long id){
        jmsTemplate.convertAndSend("Deleting product with id " + id + " from the database");
        Optional<ProductDTO> product = productService.findById(id);
        productService.deleteById(id);
        return product.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product was not found by that id")
        );
    }

}
