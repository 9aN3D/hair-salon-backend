package pl.edu.wit.hairsalon.hairdresser;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoHairdresserRepository extends MongoRepository<HairdresserDocument, String>, QuerydslPredicateExecutor<HairdresserDocument> {

}
