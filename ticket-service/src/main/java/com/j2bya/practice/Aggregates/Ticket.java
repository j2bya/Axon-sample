package com.j2bya.practice.Aggregates;




import com.j2bya.practice.Commands.CancleOrderCommand;
import com.j2bya.practice.Event.CancleOrderEvent;
import com.j2bya.practice.Event.CreateTicketEvent;
import com.j2bya.practice.Events.UpdateFailueEvent;
import com.j2bya.practice.Feign.StationEntry;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;
import static org.slf4j.LoggerFactory.getLogger;

@Data
@NoArgsConstructor
@Aggregate
public class Ticket {
  private static final Logger LOGGER = getLogger(Ticket.class);
  @AggregateIdentifier
  private String ticketId;
  private String trainShiftId;
  private String SeatNo;
  private StationEntry Start;
  private StationEntry End;

  public Ticket(String ticketId, String trainShiftId,String SeatNo, StationEntry Start ,StationEntry End){
    apply(new CreateTicketEvent(ticketId, trainShiftId, SeatNo, Start, End));
  }
  @CommandHandler
  public void on(CancleOrderCommand command){
    System.out.println(this.ticketId);
    System.out.println(command.getTicketId());
    apply(new CancleOrderEvent(command.getTicketId(), command.getTrainShiftId(), command.getSeatNo(), command.getStart(), command.getEnd()));
  }
  @EventHandler
  public void on(CancleOrderEvent event){
    System.out.println(ticketId);
    markDeleted();
  }
  @EventHandler
  public void on(CreateTicketEvent event){
    this.ticketId = event.getTicketId();
    this.SeatNo = event.getSeatNo();
    this.Start =event.getStart();
    this.End = event.getEnd();
  }
  @EventHandler
  public void on(UpdateFailueEvent event){
    System.out.println(event.getTicketId());
    markDeleted();
  }
}
