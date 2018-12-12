package com.j2bya.practice.Config;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.util.Date;

public class TimestampConvertor implements Converter<Date, Timestamp> {

  @Override
  public Timestamp convert(Date date) {
    if(date != null){
      return new Timestamp(date.getTime());
    }
    return null;
  }

}
