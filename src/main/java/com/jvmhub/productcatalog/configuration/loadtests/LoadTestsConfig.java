package com.jvmhub.productcatalog.configuration.loadtests;

import com.jvmhub.productcatalog.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("loadTests")
public class LoadTestsConfig {

  @Bean
  public LoadTestsDataProvider loadTestsDataProvider(ProductRepository productRepository){
    return new LoadTestsDataProvider(productRepository);
  }
}
