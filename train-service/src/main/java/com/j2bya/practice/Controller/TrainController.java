package com.j2bya.practice.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.j2bya.practice.Aggregate.Seat;
import com.j2bya.practice.Aggregate.Segment;
import com.j2bya.practice.Aggregate.Station;
import com.j2bya.practice.Commands.CreateTrainShiftCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequiredArgsConstructor
public class TrainController {

  private final static Logger logger = LoggerFactory.getLogger(TrainController.class);
  private final CommandGateway commandGateway;

  @PostMapping("/ct/")
  @ResponseStatus(HttpStatus.CREATED)
  public String trainback(@RequestBody JSONObject input){
    logger.warn("clear love 7777777");
    logger.warn("clear love 7777777");
    logger.warn("clear love 7777777");
    logger.warn("clear love 7777777");
    logger.warn("clear love 7777777");
    logger.warn("clear love 7777777");
    logger.warn("clear love 7777777");
    logger.warn("clear love 7777777");
    List<Station> _stations = new ArrayList<>();
    Map<String, Seat> _soldsSeats = new HashMap<>();
    TreeMap<String, Seat> _cleanSeats = new TreeMap<>();
    List<Segment> _atomSegments = new ArrayList<>();
    List<Integer> _atomSegmentsSeats = new ArrayList<>();

    String _id = input.getString("id");
    String _name = input.getString("name");
    JSONArray stations = input.getJSONArray("stations");
    Integer seatCount = input.getInteger("seatCount");

    //初始化站点
    for (Object each: stations){
      JSONObject o = (JSONObject)each;
      Station station = new Station(o.getString("Id"), o.getString("Name"), o.getInteger("No"), o.getTimestamp("StartTime"));
      _stations.add(station);
    }

    //初始化座位
    List<Segment> soldSegments = new ArrayList<>();
    for (int i = 1; i <= seatCount; i++) {
      Seat seat = new Seat(String.valueOf(i), soldSegments);
      _cleanSeats.put(seat.No, seat);
    }

    //初始化原子区间
    for (int i = 2; i <= _stations.size(); i++) {
      _atomSegments.add(new Segment(i-1, i));
      _atomSegmentsSeats.add(seatCount);
    }
  return commandGateway.sendAndWait(new CreateTrainShiftCommand(_id, _name, _stations, _soldsSeats, _cleanSeats, _atomSegments, _atomSegmentsSeats));
  }
}
