package pl.edu.wit.user.adapter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.user.adapter.model.UserDocument;

import java.util.Optional;

@Repository
public interface MongoUserRepository extends MongoRepository<UserDocument, String>, QuerydslPredicateExecutor<UserDocument> {

    Optional<UserDocument> findFirstByName(String name);

}
