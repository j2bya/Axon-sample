package com.j2bya.practice.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;


import java.util.ArrayList;
import java.util.List;


@Configuration
public class MongoConfig {

  @Bean
  public MongoCustomConversions customConversions(){
    List<Converter<?,?>> converterList = new ArrayList<Converter<?, ?>>();
    converterList.add(new TimestampConvertor());
    return new MongoCustomConversions(converterList);
  }

}
