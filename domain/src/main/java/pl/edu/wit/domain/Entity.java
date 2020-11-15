package pl.edu.wit.domain;

public abstract class Entity<ID> implements IdentifiableDomainObject<ID> {

    private final ID id;

    public Entity(ID id) {
        this.id = id;
    }

    @Override
    public ID id() {
        return id;
    }

}
