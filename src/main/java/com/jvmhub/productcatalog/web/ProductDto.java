package com.jvmhub.productcatalog.web;

import com.jvmhub.productcatalog.repository.ProductEntity;

public record ProductDto(Long id, String sku, String name, String description) {

  public static ProductDto from(ProductEntity productEntity) {
    return new ProductDto(productEntity.getId(), productEntity.getSku(), productEntity.getName(), productEntity.getDescription());
  }

}
