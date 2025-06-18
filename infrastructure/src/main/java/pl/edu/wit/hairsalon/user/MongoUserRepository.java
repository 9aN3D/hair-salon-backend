package pl.edu.wit.hairsalon.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

interface MongoUserRepository extends MongoRepository<UserDocument, String>, QuerydslPredicateExecutor<UserDocument> {

}
