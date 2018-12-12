package com.j2bya.practice.Feign;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StationEntry {
  public String Id;
  public String Name;
  public int No;
  public Timestamp StartTime;
}
