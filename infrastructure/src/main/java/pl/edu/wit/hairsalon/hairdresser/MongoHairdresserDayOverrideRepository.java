package pl.edu.wit.hairsalon.hairdresser;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;

interface MongoHairdresserDayOverrideRepository extends MongoRepository<HairdresserDayOverrideDocument, HairdresserDayOverrideIdDto>, QuerydslPredicateExecutor<HairdresserDayOverrideDocument> {

}
