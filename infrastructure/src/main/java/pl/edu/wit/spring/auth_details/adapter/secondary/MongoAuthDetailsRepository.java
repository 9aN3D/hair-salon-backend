package pl.edu.wit.spring.auth_details.adapter.secondary;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.wit.spring.auth_details.adapter.model.AuthDetails;

public interface MongoAuthDetailsRepository extends MongoRepository<AuthDetails, String> {

}
