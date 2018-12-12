package com.j2bya.practice.Commands;

import com.j2bya.practice.Feign.StationEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class CancleOrderCommand {
  @TargetAggregateIdentifier
  private String ticketId;
  private String trainShiftId;
  private String SeatNo;
  private StationEntry Start;
  private StationEntry End;
}
