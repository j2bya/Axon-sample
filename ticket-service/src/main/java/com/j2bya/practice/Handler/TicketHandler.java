package com.j2bya.practice.Handler;


import com.j2bya.practice.Aggregates.Ticket;
import com.j2bya.practice.Commands.CreateTicketCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.Repository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@RequiredArgsConstructor
public class TicketHandler{

  @Autowired
  private Repository<Ticket> ticketRepository;
  private final Logger logger = getLogger(TicketHandler.class);
  private final CommandGateway commandGateway;

  @CommandHandler
  public void on(CreateTicketCommand command){
    try {
      ticketRepository.newInstance(()->new Ticket(command.getTicketId(), command.getTrainShiftId(), command.getSeatNo(), command.getStart(), command.getEnd()));
    } catch (Exception e){
      logger.warn("ClearLove 777");
    }
  }
}
