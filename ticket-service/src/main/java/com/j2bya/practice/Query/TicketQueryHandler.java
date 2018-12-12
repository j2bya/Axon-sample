package com.j2bya.practice.Query;

import com.j2bya.practice.Event.CancleOrderEvent;
import com.j2bya.practice.Event.CreateTicketEvent;
import com.j2bya.practice.Events.UpdateFailueEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@RequiredArgsConstructor
public class TicketQueryHandler {
  private final Logger LOGGER = getLogger(TicketQueryHandler.class);
  @Autowired
  private TicketQueryRepository repository;

  @EventHandler
  public void on(CreateTicketEvent event){
    repository.save(new TicketEntry(event.getTicketId(), event.getTrainShiftId(), event.getSeatNo(), event.getStart(), event.getEnd()));
  }
  @EventHandler
  public void on(CancleOrderEvent event){
    repository.deleteById(event.getTicketId());
  }
  @EventHandler
  public void on(UpdateFailueEvent event){
    repository.deleteById(event.getTicketId());
  }
}
