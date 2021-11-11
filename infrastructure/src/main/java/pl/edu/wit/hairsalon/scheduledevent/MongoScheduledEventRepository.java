package pl.edu.wit.hairsalon.scheduledevent;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoScheduledEventRepository extends MongoRepository<ScheduledEventDocument, String>, QuerydslPredicateExecutor<ScheduledEventDocument> {

    void deleteByReservationId(String reservationId);

}
