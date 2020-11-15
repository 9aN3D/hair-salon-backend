package pl.edu.wit.domain.cqrs;

public interface QueryHandler<R, Q extends Query<R>> {

    R handle(Q query);

}
