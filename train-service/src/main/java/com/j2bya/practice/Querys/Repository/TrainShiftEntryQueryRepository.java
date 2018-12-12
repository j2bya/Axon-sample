package com.j2bya.practice.Querys.Repository;

  import com.j2bya.practice.Querys.Entry.trainShiftEntry;
  import org.springframework.data.mongodb.repository.MongoRepository;
  import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trainShift", path = "test")
public interface TrainShiftEntryQueryRepository extends MongoRepository<trainShiftEntry, String> {
}
