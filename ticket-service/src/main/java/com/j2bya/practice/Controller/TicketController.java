package com.j2bya.practice.Controller;

import com.alibaba.fastjson.JSONObject;
import com.j2bya.practice.Commands.CancleOrderCommand;
import com.j2bya.practice.Commands.CreateTicketCommand;
import com.j2bya.practice.Feign.StationEntry;
import com.j2bya.practice.Feign.TrainShiftEntry;
import com.j2bya.practice.Feign.trainShiftService;
import com.j2bya.practice.Query.TicketEntry;
import com.j2bya.practice.Query.TicketQueryRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequiredArgsConstructor
public class TicketController {
  private static final Logger logger = getLogger(TicketController.class);

  @Autowired
  private trainShiftService service;
  @Autowired
  private TicketQueryRepository repository;
  private final CommandGateway commandGateway;

  @GetMapping("/query/{id}")
  public TrainShiftEntry on(@PathVariable String id){
    return service.getProduct(id);
  }
  @PostMapping("/xc/{id}")
  public void  cancleorder(@PathVariable String id){
    logger.warn("clear lave 7777777");
    TicketEntry ticket = repository.findById(id).orElse(null);
    System.out.println(ticket);
    commandGateway.send(new CancleOrderCommand(ticket.ticketId, ticket.trainShiftId, ticket.SeatNo, ticket.Start, ticket.End));
  }
  @PostMapping("/cc")
  public void bookticket(@RequestBody JSONObject input) {
    String ticketId = input.getString("ticketId");
    String trainShiftId = input.getString("trainShiftId");
    String seatNo = input.getString("seatNo");
    int StartNo = input.getInteger("StartNo");
    int EndNo = input.getInteger("EndNo");
    TrainShiftEntry trainshift = service.getProduct(trainShiftId);
    for (StationEntry x : trainshift.get_stations()) {
      for (StationEntry y : trainshift.get_stations()) {
        if (x.No == StartNo && y.No == EndNo) {
          commandGateway.sendAndWait(new CreateTicketCommand(ticketId, trainShiftId, seatNo, x, y));
        }
      }
    }
  }
}
