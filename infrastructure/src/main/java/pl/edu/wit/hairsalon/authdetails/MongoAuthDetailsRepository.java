package pl.edu.wit.hairsalon.authdetails;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

interface MongoAuthDetailsRepository extends MongoRepository<AuthDetailsDocument, String>, QuerydslPredicateExecutor<AuthDetailsDocument> {

    Optional<AuthDetailsDocument> findOneByEmail(String email);

}
