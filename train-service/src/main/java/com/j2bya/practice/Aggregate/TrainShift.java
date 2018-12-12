package com.j2bya.practice.Aggregate;

import com.j2bya.practice.Command.RollBackTrainShiftCommand;
import com.j2bya.practice.Events.*;
import com.j2bya.practice.Commands.CreateTrainShiftCommand;


import com.j2bya.practice.Command.UpdateTrainShiftStateCommand;
import com.j2bya.practice.Dto.SegmentDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.commandhandling.model.ForwardMatchingInstances;
import org.axonframework.eventhandling.*;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.*;

@Data
@NoArgsConstructor
@Aggregate
public class TrainShift {

  @Autowired
  private EventBus eventBus;
  @Autowired
  private EventSourcingRepository eventSourcingRepository;
  @AggregateIdentifier
  private String trainShiftId;
  private String trainName;
  @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class, routingKey = "soldSeatNo")
  private Map<String, Seat> _soldSeats;
  @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class, routingKey = "cleanSeatNo")
  private TreeMap<String, Seat> _cleanSeats;
  private List<Station> _stations;
  private List<Segment> _atomSegments;
  private List<Integer> _atomSegmentsSeats;


  @CommandHandler
  public TrainShift(CreateTrainShiftCommand command){
    apply(new TrainShiftCreatedEvent(command.getTrainShiftId(), command.getTrainShiftName(), command.get_stations(), command.get_soldSeats(), command.get_cleanSeats(), command.get_atomSegments(), command.get_atomSegmentsSeats()));
  }
  @CommandHandler
  public void on(UpdateTrainShiftStateCommand command){
    List<SegmentDto> selectSegments = new ArrayList<>();
    if(command.getTicketSegment().getStartNo() > 0 && command.getTicketSegment().getEndNo() < _atomSegments.get(_atomSegments.size()-1).EndNo){
      if (!_atomSegmentsSeats.contains(0)){
        for (Segment x: _atomSegments){
          Integer y = _atomSegmentsSeats.get(_atomSegments.indexOf(x));
          if(command.getTicketSegment().getStartNo() <= x.getStartNo() && command.getTicketSegment().getEndNo() >= x.getEndNo() ){
              SegmentDto c = new SegmentDto(x.getStartNo(), x.getEndNo());
              selectSegments.add(c);
          }
        }
        String No = command.getSeatNo();
        Segment segment = new Segment(command.getTicketSegment().getStartNo(), command.getTicketSegment().getEndNo());
        if (_soldSeats.containsKey(No)) {
          if (_soldSeats.get(command.getSeatNo()).CanSellToSegment(segment)) {
            apply(new UpdateTrainshiftSoldSeatsEvent(command.getTicketId(), command.getTrainShiftId(), command.getSeatNo(), command.getTicketSegment(), selectSegments));
          }
          else{
            apply(new UpdateFailueEvent(command.getTicketId(), command.getTrainShiftId()));
          }
        }
        else{
          apply(new UpdateTrainShiftCleanSeatEvent(command.getTicketId(), command.getTrainShiftId(), command.getSeatNo(), command.getTicketSegment(), selectSegments));
        }
      }
      else {
        apply(new UpdateFailueEvent(command.getTicketId(), command.getTrainShiftId()));
      }
    }
    else {
      apply(new UpdateFailueEvent(command.getTicketId(), command.getTrainShiftId()));
    }
  }
  @EventSourcingHandler
  public void on(TrainShiftCreatedEvent event){
    this.trainShiftId = event.getTrainShiftId();
    this.trainName = event.getTrainShiftName();
    this._stations = event.get_stations();
    this._soldSeats = event.get_soldSeats();
    this._cleanSeats = event.get_cleanSeats();
    this._atomSegments = event.get_atomSegments();
    this._atomSegmentsSeats = event.get_atomSegmentsSeats();
  }

  @EventHandler
  public void on(UpdateTrainshiftSoldSeatsEvent event){
    Segment segment =new Segment(event.getTicketSegment().getStartNo(), event.getTicketSegment().getEndNo());

    //Update sold Seats
    Seat seat =this._soldSeats.get(event.getSoldSeatNo());
    seat._soldSegments.add(segment);
    this._soldSeats.put(seat.No, seat);


    //Update every segment's seat number;
    for (SegmentDto x: event.getTicketSegments()){
      int num = _atomSegments.indexOf(new Segment(x.getStartNo(), x.getEndNo()));
      _atomSegmentsSeats.set(num, _atomSegmentsSeats.get(num)-1);
    }
    }
  @EventSourcingHandler
  public void on(UpdateTrainShiftCleanSeatEvent event){
    Segment segment =new Segment(event.getTicketSegment().getStartNo(), event.getTicketSegment().getEndNo());

    //Update clean seats
    Seat seat =this._cleanSeats.get(event.getCleanSeatNo());
    seat._soldSegments.add(segment);
    this._soldSeats.put(seat.No, seat);
    this._cleanSeats.remove(seat.No);


    //Update every segment's seat number;
    for (SegmentDto x: event.getTicketSegments()){
      int num = _atomSegments.indexOf(new Segment(x.getStartNo(), x.getEndNo()));
      _atomSegmentsSeats.set(num, _atomSegmentsSeats.get(num)-1);
    }
  }
  @CommandHandler
  public void on(RollBackTrainShiftCommand command) {
    apply(new RollBackTrainShiftEvent(command.getTicketId(), command.getTrainShiftId(), command.getSeatNo(), command.getStartNo(), command.getEndNo()));
  }
  @EventHandler
  public void on(RollBackTrainShiftEvent event){
    Seat seat = _soldSeats.get(event.getSeatNo());
   List<Segment> soldSegments = seat.get_soldSegments();
   if (soldSegments.size() == 1){
     soldSegments.clear();
     _soldSeats.remove(event.getSeatNo());
     _cleanSeats.put(event.getSeatNo(), seat);
   }
   else {soldSegments.remove(new Segment(event.getStartNo(), event.getEndNo()));}
   for (int i = event.getStartNo()-1 ; i < event.getEndNo()-1; i++){
     _atomSegmentsSeats.set(i, _atomSegmentsSeats.get(i)+1);
   }
   System.out.println(_cleanSeats);
   System.out.println(_soldSeats);
   System.out.println(_atomSegmentsSeats);
  }
  }

