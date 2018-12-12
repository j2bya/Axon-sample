package com.j2bya.practice.Saga;


  import com.j2bya.practice.Event.CreateTicketEvent;
  import org.axonframework.commandhandling.gateway.CommandGateway;
  import org.axonframework.eventhandling.saga.EndSaga;
  import org.axonframework.eventhandling.saga.SagaEventHandler;
  import org.axonframework.eventhandling.saga.StartSaga;
  import org.axonframework.spring.stereotype.Saga;
  import org.slf4j.Logger;
  import org.springframework.beans.factory.annotation.Autowired;
  import com.j2bya.practice.Command.UpdateTrainShiftStateCommand;
  import com.j2bya.practice.Dto.SegmentDto;
  import com.j2bya.practice.Events.UpdateFailueEvent;
  import com.j2bya.practice.Events.UpdateTrainShiftCleanSeatEvent;
  import com.j2bya.practice.Events.UpdateTrainshiftSoldSeatsEvent;

  import static org.slf4j.LoggerFactory.getLogger;

@Saga
public class BookTicketSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  private static final Logger logger = getLogger(BookTicketSaga.class);


  @StartSaga
  @SagaEventHandler(associationProperty = "ticketId")
  public void on(CreateTicketEvent event){
    commandGateway.send(new UpdateTrainShiftStateCommand(event.getTicketId(), event.getTrainShiftId(), event.getSeatNo(), new SegmentDto(event.getStart().getNo(), event.getEnd().getNo())));
  }
  @EndSaga
  @SagaEventHandler(associationProperty = "ticketId")
  public void on(UpdateTrainshiftSoldSeatsEvent event){
    logger.warn("clearlove 7777777  sold");
  }
  @EndSaga
  @SagaEventHandler(associationProperty = "ticketId")
  public void on(UpdateTrainShiftCleanSeatEvent event){
    logger.warn("clearlove 7777777 clean");
  }
  @EndSaga
  @SagaEventHandler(associationProperty = "ticketId")
  public void on(UpdateFailueEvent event){
    logger.warn("clearlove 7777777 false");
  }
}
