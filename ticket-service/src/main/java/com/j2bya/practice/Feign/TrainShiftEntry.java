package com.j2bya.practice.Feign;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Data
@NoArgsConstructor
public class TrainShiftEntry {
  private String _id;
  private String trainShiftName;
  private List<StationEntry> _stations;
  private Map<String, SeatEntry> _soldSeats;
  private TreeMap<String, SeatEntry> _cleanSeats;
  private List<SegmentEntry> _atomSegments;
  private List<Integer> _atomSegmentsSeats;
}
