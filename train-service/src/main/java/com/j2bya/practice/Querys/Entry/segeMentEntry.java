package com.j2bya.practice.Querys.Entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@AllArgsConstructor
public class segeMentEntry {
  public int StartNo;
  public int EndNo;
}
