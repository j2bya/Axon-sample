package com.j2bya.practice.Aggregate;


import com.j2bya.practice.Events.UpdateTrainShiftCleanSeatEvent;
import com.j2bya.practice.Events.UpdateTrainshiftSoldSeatsEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.eventhandling.EventHandler;

import java.util.List;
@Data
@AllArgsConstructor
public class Seat {
  @EntityId
  public String No;
  public List<Segment> _soldSegments;

  @EventHandler
  public void on(UpdateTrainShiftCleanSeatEvent event){
    Segment segment = new Segment(event.getTicketSegment().getStartNo(), event.getTicketSegment().getEndNo());
    _soldSegments.add(segment);
  }

  @EventHandler
  public void on(UpdateTrainshiftSoldSeatsEvent event){
    Segment segment = new Segment(event.getTicketSegment().getStartNo(), event.getTicketSegment().getEndNo());
    _soldSegments.add(segment);
  }

  public boolean CanSellToSegment(Segment segment){
    for (Segment key: this._soldSegments
    ) if (IsSegmentIntersect(key, segment)){
      return false;
    }
    return true;
  }
  public boolean IsSegmentIntersect(Segment segment1, Segment segment2){
    return (segment1.StartNo > segment2.StartNo && segment1.StartNo < segment2.EndNo)
      ||(segment1.EndNo > segment2.StartNo &&segment1.EndNo <segment2.EndNo)
      ||(segment1.StartNo <= segment2.EndNo && segment1.EndNo >= segment2.EndNo)
      ||(segment1.StartNo >= segment2.StartNo && segment1.EndNo <= segment2.EndNo);
  }
}
