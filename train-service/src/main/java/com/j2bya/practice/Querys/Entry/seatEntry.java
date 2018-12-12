package com.j2bya.practice.Querys.Entry;

  import lombok.AllArgsConstructor;
  import lombok.Data;
  import org.springframework.data.mongodb.core.mapping.Document;

  import java.util.List;

@Data
@Document
@AllArgsConstructor
public class seatEntry {
  public String seatNo;
  public List<segeMentEntry> _soldSegments;
}
