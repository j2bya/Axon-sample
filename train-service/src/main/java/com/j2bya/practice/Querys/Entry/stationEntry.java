package com.j2bya.practice.Querys.Entry;

  import lombok.AllArgsConstructor;
  import lombok.Data;
  import org.springframework.data.mongodb.core.mapping.Document;

  import java.sql.Timestamp;

@Data
@Document
@AllArgsConstructor
public class stationEntry {
  public String Id;
  public String Name;
  public int No;
  public Timestamp StartTime;
}
