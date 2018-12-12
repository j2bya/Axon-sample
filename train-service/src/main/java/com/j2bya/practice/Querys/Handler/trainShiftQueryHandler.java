package com.j2bya.practice.Querys.Handler;


  import com.j2bya.practice.Dto.SegmentDto;
  import com.j2bya.practice.Events.RollBackTrainShiftEvent;
  import com.j2bya.practice.Events.TrainShiftCreatedEvent;
  import com.j2bya.practice.Events.UpdateTrainShiftCleanSeatEvent;
  import com.j2bya.practice.Events.UpdateTrainshiftSoldSeatsEvent;
  import com.j2bya.practice.Querys.Entry.trainShiftEntry;
  import com.j2bya.practice.Querys.Entry.stationEntry;


  import com.j2bya.practice.Querys.Entry.seatEntry;
  import com.j2bya.practice.Querys.Entry.segeMentEntry;
  import com.j2bya.practice.Querys.Repository.TrainShiftEntryQueryRepository;
  import org.axonframework.eventhandling.EventHandler;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;

  import java.util.*;

@Component
public class trainShiftQueryHandler {

  @Autowired
  TrainShiftEntryQueryRepository repository;

  @EventHandler
  public void on(TrainShiftCreatedEvent event){
    List<stationEntry> _stations = new ArrayList<>();
    Map<String, seatEntry> _soldsSeats = new HashMap<>();
    List<segeMentEntry> _soldSegments = new ArrayList<>();
    TreeMap<String, seatEntry> _cleanSeats = new TreeMap<>();
    List<segeMentEntry> _atomSegments = new ArrayList<>();

    //转化为entry
    event.get_stations().forEach(station -> {
      _stations.add(new stationEntry(station.getId(), station.getName(), station.getNo(), station.getStartTime()));
    });

    event.get_soldSeats().forEach(((integer, seat) -> {
      _soldsSeats.put(integer, new seatEntry(
        seat.getNo()
        ,_soldSegments
      ));
    }));

    event.get_cleanSeats().forEach(((integer, seat) -> {
      _cleanSeats.put(integer, new seatEntry(
        seat.getNo(),
        _soldSegments
      ));
    }));

    event.get_atomSegments().forEach(key -> {
      _atomSegments.add(new segeMentEntry(key.getStartNo(), key.getEndNo()));
    });

    List<Integer> _atomSegmentsSeats = event.get_atomSegmentsSeats();
    trainShiftEntry entry = new trainShiftEntry(event.getTrainShiftId(), event.getTrainShiftName(), _stations, _soldsSeats, _cleanSeats, _atomSegments, _atomSegmentsSeats);
    repository.save(entry);
  }

  @EventHandler
  public void on(UpdateTrainshiftSoldSeatsEvent event){
    trainShiftEntry trainshift = repository.findById(event.getTrainShiftId()).get();
    List<segeMentEntry> atomSegments = trainshift.get_atomSegments();
    List<Integer> atomSegmentsSeats = trainshift.get_atomSegmentsSeats();
    Map<String, seatEntry> soldSeats = trainshift.get_soldSeats();
    Map<String, seatEntry> cleanSeats = trainshift.get_cleanSeats();
    String seatNo =event.getSoldSeatNo();
    seatEntry seat = soldSeats.get(seatNo);
    List<segeMentEntry> soldSegments = seat.get_soldSegments();
    segeMentEntry segment = new segeMentEntry(event.getTicketSegment().getStartNo(), event.getTicketSegment().getEndNo());
    for (SegmentDto x: event.getTicketSegments()){
      int num =  atomSegments.indexOf(new segeMentEntry(x.getStartNo(), x.getEndNo()));
      atomSegmentsSeats.set(num, atomSegmentsSeats.get(num)-1);
    }
    soldSegments.add(segment);
    soldSeats.put(seatNo, seat);
    repository.save(trainshift);
  }
  @EventHandler
  public void on(UpdateTrainShiftCleanSeatEvent event){
    trainShiftEntry trainshift = repository.findById(event.getTrainShiftId()).get();
    List<segeMentEntry> atomSegments = trainshift.get_atomSegments();
    List<Integer> atomSegmentsSeats = trainshift.get_atomSegmentsSeats();
    Map<String, seatEntry> soldSeats = trainshift.get_soldSeats();
    Map<String, seatEntry> cleanSeats = trainshift.get_cleanSeats();
    String seatNo =event.getCleanSeatNo();
    seatEntry seat = cleanSeats.get(seatNo);
    List<segeMentEntry> soldSegments = new ArrayList<>();
    segeMentEntry segment = new segeMentEntry(event.getTicketSegment().getStartNo(), event.getTicketSegment().getEndNo());
    for (SegmentDto x: event.getTicketSegments()){
      int num =  atomSegments.indexOf(new segeMentEntry(x.getStartNo(), x.getEndNo()));
      atomSegmentsSeats.set(num, atomSegmentsSeats.get(num)-1);
    }
    soldSegments.add(segment);
    seat.set_soldSegments(soldSegments);
    soldSeats.put(seatNo, seat);
    cleanSeats.remove(seatNo);
    repository.save(trainshift);
  }
  @EventHandler
  public void on(RollBackTrainShiftEvent event){
    trainShiftEntry trainShift = repository.findById(event.getTrainShiftId()).get();
    seatEntry seat = trainShift.get_soldSeats().get(event.getSeatNo());
    List<segeMentEntry> soldSegments = seat.get_soldSegments();
    segeMentEntry segment = new segeMentEntry(event.getStartNo(), event.getEndNo());
    if (soldSegments.size() == 1){
      soldSegments.clear();
      trainShift.get_soldSeats().remove(event.getSeatNo());
      trainShift.get_cleanSeats().put(event.getSeatNo(), seat);
    }
    soldSegments.remove(segment);
    for (int i = event.getStartNo()-1 ; i < event.getEndNo()-1; i++){
      trainShift.get_atomSegmentsSeats().set(i, trainShift.get_atomSegmentsSeats().get(i)+1);
    }
    repository.save(trainShift);
  }
}
