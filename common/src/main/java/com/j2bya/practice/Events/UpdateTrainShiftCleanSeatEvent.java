package com.j2bya.practice.Events;


import com.j2bya.practice.Dto.SegmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateTrainShiftCleanSeatEvent {
  @TargetAggregateIdentifier
  private String ticketId;
  @TargetAggregateIdentifier
  private String trainShiftId;
  @TargetAggregateIdentifier
  private String cleanSeatNo;
  private SegmentDto ticketSegment;
  private List<SegmentDto> ticketSegments;
}
