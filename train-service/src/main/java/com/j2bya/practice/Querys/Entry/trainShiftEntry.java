package com.j2bya.practice.Querys.Entry;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class trainShiftEntry {
  @Id
  private String _id;
  private String trainShiftName;
  private List<stationEntry> _stations;
  private Map<String, seatEntry> _soldSeats;
  private TreeMap<String, seatEntry> _cleanSeats;
  private List<segeMentEntry> _atomSegments;
  private List<Integer> _atomSegmentsSeats;
}
