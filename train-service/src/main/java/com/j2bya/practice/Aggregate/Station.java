package com.j2bya.practice.Aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
public class Station {
  public String Id;
  public String Name;
  public int No;
  public Timestamp StartTime;
}
