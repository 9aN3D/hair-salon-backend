package pl.edu.wit.hairsalon.idgenerator;

import org.bson.types.ObjectId;
import pl.edu.wit.hairsalon.sharedkernel.port.secondary.IdGenerator;

class MongoIdGenerator implements IdGenerator {

    @Override
    public String generate() {
        return new ObjectId().toString();
    }

    @Override
    public Boolean isValid(String id) {
        return ObjectId.isValid(id);
    }

}
