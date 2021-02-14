package pl.edu.wit.spring.adapter.persistence.auth_details;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.AuthDetailsDocument;

import java.util.Optional;

@Repository
public interface MongoAuthDetailsRepository extends MongoRepository<AuthDetailsDocument, String>, QuerydslPredicateExecutor<AuthDetailsDocument> {

    Optional<AuthDetailsDocument> findOneByEmail(String email);

}
