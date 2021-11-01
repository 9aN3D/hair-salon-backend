package pl.edu.wit.hairsalon.authdetails;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface MongoAuthDetailsRepository extends MongoRepository<AuthDetailsDocument, String>, QuerydslPredicateExecutor<AuthDetailsDocument> {

    Optional<AuthDetailsDocument> findOneByEmail(String email);

}
