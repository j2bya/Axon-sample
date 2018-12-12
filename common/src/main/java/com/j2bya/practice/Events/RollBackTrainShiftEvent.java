package com.j2bya.practice.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RollBackTrainShiftEvent {
  @TargetAggregateIdentifier
  private String ticketId;
  @TargetAggregateIdentifier
  private String trainShiftId;
  private String seatNo;
  private int StartNo;
  private int EndNo;
}
