package pl.edu.wit.spring.id_generator;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import pl.edu.wit.IdGenerator;

@Component
public class MongoIdGenerator implements IdGenerator {

    @Override
    public String generate() {
        return new ObjectId().toString();
    }

}
