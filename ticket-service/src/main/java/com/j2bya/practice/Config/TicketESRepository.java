package com.j2bya.practice.Config;


import com.j2bya.practice.Aggregates.Ticket;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketESRepository {
  @Bean(name = "ticketRepository")
  public Repository<Ticket> Repository(EventStore eventStore){
    return new EventSourcingRepository<>(Ticket.class, eventStore);
  }
}
