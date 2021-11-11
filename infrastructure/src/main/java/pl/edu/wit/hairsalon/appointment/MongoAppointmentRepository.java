package pl.edu.wit.hairsalon.appointment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoAppointmentRepository extends MongoRepository<AppointmentDocument, String>, QuerydslPredicateExecutor<AppointmentDocument> {

}
