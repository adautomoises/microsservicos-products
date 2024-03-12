package com.example.demo.controller;

import com.example.demo.model.product.ProductDTO;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getProducts() {
        return productService.getAll();
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<ProductDTO> getProductByCategory(@PathVariable Long categoriaId) {
        return productService.getProductByCategoryId(categoriaId);
    }

    @GetMapping("/{productIdentifier}")
    public ProductDTO findById(
            @PathVariable String productIdentifier) {
        return productService
                .findByProductIdentifier(productIdentifier);
    }

    @GetMapping("/lista")
    public Page<ProductDTO> getProductsPage(Pageable pageable) {
        return productService.getAllPage(pageable);
    }

    @PostMapping
    public ProductDTO newProduct(@Valid @RequestBody ProductDTO userDTO) {
        return productService.save(userDTO);
    }

    @PostMapping("/{id}")
    public ProductDTO editProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.editProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
