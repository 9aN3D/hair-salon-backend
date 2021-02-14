package pl.edu.wit.application.port.secondary;

public interface IdGenerator {

    String generate();

    Boolean isValid(String id);

}
