package pl.edu.wit.domain.base;

import pl.edu.wit.domain.IdGenerator;

import java.util.UUID;

public final class TestGenerator implements IdGenerator {

    private final UUID value;

    public TestGenerator() {
        this.value = UUID.randomUUID();
    }

    @Override
    public String generate() {
        return value.toString();
    }

}
