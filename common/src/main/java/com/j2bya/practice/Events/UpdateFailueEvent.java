package com.j2bya.practice.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class UpdateFailueEvent {
  @TargetAggregateIdentifier
  private String ticketId;
  private String trainShiftId;
}
