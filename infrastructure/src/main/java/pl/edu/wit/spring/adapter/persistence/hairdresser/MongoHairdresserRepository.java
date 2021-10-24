package pl.edu.wit.spring.adapter.persistence.hairdresser;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.spring.adapter.persistence.hairdresser.model.HairdresserDocument;

@Repository
public interface MongoHairdresserRepository extends MongoRepository<HairdresserDocument, String>, QuerydslPredicateExecutor<HairdresserDocument> {

}
