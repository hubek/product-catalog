package com.jvmhub.productcatalog.web;

import com.jvmhub.productcatalog.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductRepository productRepository;

  Set<ProductDto> productsRepository = Set.of(
      new ProductDto(1l, "AAA", "Product 1", "This is product 1."),
      new ProductDto(2l, "BBB", "Product 2", "This is product 2."),
      new ProductDto(3l, "CCC", "Product 3", "This is product 3.")
  );

  @GetMapping
  public List<ProductDto> getProducts() {
    return productRepository.findAll().stream()
        .map(ProductDto::from)
        .toList();
  }

  @GetMapping("/{productId}")
  public ProductDto getProduct(@PathVariable Long productId) {
    return productRepository.findById(productId)
        .map(ProductDto::from)
        .orElseThrow(() -> new NoSuchElementException("Product not found, id: " + productId));
  }
}
