package pl.edu.wit.hairsalon.reservation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoReservationRepository extends MongoRepository<ReservationDocument, String>, QuerydslPredicateExecutor<ReservationDocument> {

}
