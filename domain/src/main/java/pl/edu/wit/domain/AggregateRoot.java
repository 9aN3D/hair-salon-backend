package pl.edu.wit.domain;

public abstract class AggregateRoot<ID> extends Entity<ID> {

    public AggregateRoot(ID id) {
        super(id);
    }

}
