package com.j2bya.practice.Query;

import com.j2bya.practice.Feign.StationEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class TicketEntry {
  @Id
  public String ticketId;
  public String trainShiftId;
  public String SeatNo;
  public StationEntry Start;
  public StationEntry End;
}
