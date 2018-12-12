package com.j2bya.practice.Query;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

@Configuration
public interface TicketQueryRepository extends MongoRepository<TicketEntry, String> {
}
