package com.j2bya.practice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CloudConfig {
  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
