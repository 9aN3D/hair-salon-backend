package pl.edu.wit.spring.adapter.repository.auth_details;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wit.spring.adapter.repository.auth_details.document.AuthDetailsDocument;

import java.util.Optional;

@Repository
public interface MongoAuthDetailsRepository extends MongoRepository<AuthDetailsDocument, String> {

    Optional<AuthDetailsDocument> findOneByEmail(String email);

}
