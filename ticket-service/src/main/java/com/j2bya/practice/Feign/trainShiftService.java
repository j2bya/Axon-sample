package com.j2bya.practice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "train-service")
public interface trainShiftService {
  @GetMapping("/test/{id}")
  TrainShiftEntry getProduct(@PathVariable("id") String id);
}
