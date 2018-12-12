package com.j2bya.practice.Command;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import com.j2bya.practice.Dto.SegmentDto;

@Data
@AllArgsConstructor
public class UpdateTrainShiftStateCommand {
  private String ticketId;
  @TargetAggregateIdentifier
  private String trainShiftId;
  private String seatNo;
  private SegmentDto ticketSegment;
}
