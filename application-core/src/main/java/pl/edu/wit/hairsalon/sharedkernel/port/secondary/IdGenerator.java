package pl.edu.wit.hairsalon.sharedkernel.port.secondary;

public interface IdGenerator {

    String generate();

    Boolean isValid(String id);

}
