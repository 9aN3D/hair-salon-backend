package pl.edu.wit.auth.details.adapter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.auth.details.adapter.model.AuthDetailsDocument;

import java.util.Optional;

@Repository
public interface MongoAuthDetailsRepository extends MongoRepository<AuthDetailsDocument, String>, QuerydslPredicateExecutor<AuthDetailsDocument> {

    Optional<AuthDetailsDocument> findOneByEmail(String email);

}
