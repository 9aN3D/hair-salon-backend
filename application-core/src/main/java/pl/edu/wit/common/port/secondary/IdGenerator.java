package pl.edu.wit.common.port.secondary;

public interface IdGenerator {

    String generate();

    Boolean isValid(String id);

}
