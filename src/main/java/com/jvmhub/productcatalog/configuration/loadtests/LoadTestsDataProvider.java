package com.jvmhub.productcatalog.configuration.loadtests;

import com.jvmhub.productcatalog.repository.ProductEntity;
import com.jvmhub.productcatalog.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoadTestsDataProvider {

  private static final long maxNumberOfProducts = 1000000;
  private static final long numberOfProductsToInsert = 1000000;

  private final ProductRepository productRepository;

  private static Random random = new Random();

  @PostConstruct
  public void populateWithLoadTestData() {
    long productsCount = productRepository.count();
    if (productsCount < maxNumberOfProducts) {
      Instant startTime = Instant.now();
      log.info("Load tests data insert started...");
      insertProducts(numberOfProductsToInsert);
      log.info("Load tests data insert finished in: {} seconds, inserted products: {}",
          (float) Duration.between(startTime, Instant.now()).toMillis() / 1000,
          numberOfProductsToInsert
      );
    } else {
      log.info("No need to add load tests products, current count: {}", productsCount);
    }
  }

  private void insertProducts(long numberOfProducts) {
    List<ProductEntity> products = new ArrayList<>();
    for (int i = 0; i < numberOfProducts; i++) {
      ProductEntity product = ProductEntity.builder()
          .sku(UUID.randomUUID().toString())
          .name(randomName())
          .description(randomDescription())
          .build();
      products.add(product);
      if (i % 10000 == 0) {
        productRepository.saveAllAndFlush(products);
        products = new ArrayList<>();
        log.info("Chunk saved...");
      }
    }
    if (products.size() > 0) {
      productRepository.saveAllAndFlush(products);
      log.info("Last chunk saved...");
    }
  }

  private String randomName() {
    StringBuilder name = new StringBuilder();
    name.append(brand[random.nextInt(brand.length)]);
    name.append(" ");
    name.append(adjective[random.nextInt(adjective.length)]);
    name.append(" ");
    name.append(productType[random.nextInt(productType.length)]);
    return name.toString();
  }

  private String randomDescription() {
    Set descSet = new HashSet<String>();
    for (int i = 0; i < 3; i++) {
      descSet.add(description[random.nextInt(description.length)]);
    }
    StringBuilder description = new StringBuilder();
    descSet.forEach(d -> description.append(d).append(" "));
    description.deleteCharAt(description.length() - 1);
    return description.toString();
  }


  public static String[] brand = {"sony", "bose", "samsung", "jbl", "yamaha", "dyson", "miele", "lenovo", "microsoft", "acer", "asus", "dell", "msi"};
  public static String[] adjective = {"nice", "cheap", "big", "small", "expensive", "popular", "fast", "slow", "quiet", "black", "white", "red", "green"};
  public static String[] productType = {"phone", "notebook", "tablet", "monitor", "console", "headphones", "speakers", "soundbar", "subwoofer", "vacuum"};

  public static String[] description = {
      "This is very nice product.",
      "Everyone want to have it.",
      "The product is of very high quality.",
      "It is recommended for adults.",
      "Easy to use.",
      "It is new generation.",
      "It will change your life.",
      "Recommended by lots of bloggers.",
      "You can return it within 30 days.",
      "It has replaceable parts.",
      "Has a 2-year warranty."
  };

}
