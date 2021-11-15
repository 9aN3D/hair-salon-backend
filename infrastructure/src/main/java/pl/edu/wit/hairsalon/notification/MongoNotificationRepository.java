package pl.edu.wit.hairsalon.notification;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoNotificationRepository extends MongoRepository<NotificationDocument, String>, QuerydslPredicateExecutor<NotificationDocument> {

}
