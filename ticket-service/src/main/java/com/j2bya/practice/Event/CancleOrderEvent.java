package com.j2bya.practice.Event;

import com.j2bya.practice.Feign.StationEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CancleOrderEvent {
  @TargetAggregateIdentifier
  private String ticketId;
  private String trainShiftId;
  private String SeatNo;
  private StationEntry Start;
  private StationEntry End;
}
