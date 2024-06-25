package hr.algreba.valentinherve.productapp.controller;

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

//    @Secured("ROLE_ADMIN")
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    public ProductDTO save(@Valid @RequestBody final HardwareCommand command){
//        return productService.save(command)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Hardware with the same code already exists"));
//    }
//
//    @Secured("ROLE_ADMIN")
//    @PutMapping("{code}")
//    public ProductDTO update(@PathVariable String code, @Valid @RequestBody final HardwareUpdateCommand updatedHardwareCommand){
//        return productService.update(code, updatedHardwareCommand)
//                .orElseThrow(
//                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hardware was not found by that code")
//                );
//    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        productService.deleteById(id);
    }

}
