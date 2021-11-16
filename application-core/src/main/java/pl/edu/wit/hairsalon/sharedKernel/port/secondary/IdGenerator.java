package pl.edu.wit.hairsalon.sharedKernel.port.secondary;

public interface IdGenerator {

    String generate();

    Boolean isValid(String id);

}
