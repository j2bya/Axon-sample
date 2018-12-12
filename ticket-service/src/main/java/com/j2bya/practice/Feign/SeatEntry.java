package com.j2bya.practice.Feign;

import lombok.Data;

import java.util.List;

@Data
public class SeatEntry {
  public int No;
  public List<SegmentEntry> _soldSegments;
}
