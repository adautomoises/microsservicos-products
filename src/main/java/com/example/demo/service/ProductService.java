package com.example.demo.service;

import com.example.demo.model.product.Product;
import com.example.demo.model.product.ProductDTO;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDTO> getAll() {
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByCategoryId(Long categoryId) {
        List<Product> products = productRepository.getProductByCategory(categoryId);

        return products
                .stream()
                .map(ProductDTO::convert)
                .collect(Collectors.toList());
    }

    public ProductDTO findByProductIdentifier(String productIdentifier) {
        Product product = productRepository.findByProductIdentifier(productIdentifier);

        if (product != null) {
            return ProductDTO.convert(product);
        }
        return null;
    }

    public ProductDTO save(ProductDTO productDTO) {
        Product product = productRepository.save(Product.convert(productDTO));
        return ProductDTO.convert(product);
    }

    public void delete(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.ifPresent(productRepository::delete);
    }

    public ProductDTO editProduct(long id, ProductDTO dto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Produto não encontrado"));
        if (dto.getNome() != null || !dto.getNome().isEmpty()) {
            product.setNome(dto.getNome());
        }
        if (dto.getPreco() != null) {
            product.setPreco(dto.getPreco());
        }
        return ProductDTO.convert(productRepository.save(product));
    }

    public Page<ProductDTO> getAllPage(Pageable page) {
        Page<Product> users = productRepository.findAll(page);
        return users.map(ProductDTO::convert);
    }
}