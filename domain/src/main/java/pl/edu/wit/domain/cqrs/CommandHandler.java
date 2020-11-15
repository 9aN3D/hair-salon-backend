package pl.edu.wit.domain.cqrs;

public interface CommandHandler<R, C extends Command<R>> {

    R handle(C command);

}
