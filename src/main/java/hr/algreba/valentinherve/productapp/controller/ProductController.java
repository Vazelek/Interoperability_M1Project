package hr.algreba.valentinherve.productapp.controller;

import hr.algreba.valentinherve.productapp.domain.Product;
import hr.algreba.valentinherve.productapp.dto.ProductDTO;
import hr.algreba.valentinherve.productapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.findAll();
    }

    @GetMapping("{id}")
    public ProductDTO getById(@PathVariable final Long id) {
        return productService.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product was not found by that id")
                );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDTO save(@RequestBody Product product){
        return productService.save(product)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Product with the same id already exists"));
    }

    @PutMapping("{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody final Product updatedProduct){
        return productService.update(id, updatedProduct)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product was not found by that id")
                );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        productService.deleteById(id);
    }

}
