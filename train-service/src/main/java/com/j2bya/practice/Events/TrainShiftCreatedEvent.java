package com.j2bya.practice.Events;

import com.j2bya.practice.Aggregate.Seat;
import com.j2bya.practice.Aggregate.Segment;
import com.j2bya.practice.Aggregate.Station;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@AllArgsConstructor
public class TrainShiftCreatedEvent {
  @TargetAggregateIdentifier
  private String trainShiftId;
  private String trainShiftName;
  private List<Station> _stations;
  private Map<String, Seat> _soldSeats;
  private TreeMap<String, Seat> _cleanSeats;
  private List<Segment> _atomSegments;
  private List<Integer> _atomSegmentsSeats;
}
