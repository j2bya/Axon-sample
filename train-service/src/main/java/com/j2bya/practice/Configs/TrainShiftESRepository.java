package com.j2bya.practice.Configs;

import com.j2bya.practice.Aggregate.TrainShift;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainShiftESRepository {

  @Bean(name = "trainShiftRepository")
  public Repository<TrainShift> repository(EventStore eventStore){
    return new EventSourcingRepository<>(TrainShift.class, eventStore);
  }

}
