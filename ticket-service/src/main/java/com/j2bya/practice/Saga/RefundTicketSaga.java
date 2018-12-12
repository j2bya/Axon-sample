package com.j2bya.practice.Saga;

  import com.j2bya.practice.Command.RollBackTrainShiftCommand;
  import com.j2bya.practice.Event.CancleOrderEvent;
  import com.j2bya.practice.Events.RollBackTrainShiftEvent;
  import org.axonframework.commandhandling.gateway.CommandGateway;
  import org.axonframework.eventhandling.saga.EndSaga;
  import org.axonframework.eventhandling.saga.SagaEventHandler;
  import org.axonframework.eventhandling.saga.StartSaga;
  import org.axonframework.spring.stereotype.Saga;
  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class RefundTicketSaga {

  private final static Logger logger = LoggerFactory.getLogger(RefundTicketSaga.class);
  @Autowired
  private transient CommandGateway commandGateway;

  @StartSaga
  @SagaEventHandler(associationProperty = "ticketId")
  public void on(CancleOrderEvent event){
    commandGateway.send(new RollBackTrainShiftCommand(event.getTicketId(), event.getTrainShiftId(), event.getSeatNo(),event.getStart().No, event.getEnd().getNo()));
  }
  @EndSaga
  @SagaEventHandler(associationProperty = "ticketId")
  public void on(RollBackTrainShiftEvent event){
    logger.warn("clearlove nb 7777777");
  }
}
