package com.j2bya.practice.Command;

  import lombok.AllArgsConstructor;
  import lombok.Data;
  import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RollBackTrainShiftCommand {
  private String ticketId;
  @TargetAggregateIdentifier
  private String trainShiftId;
  private String seatNo;
  private int StartNo;
  private int EndNo;
}
