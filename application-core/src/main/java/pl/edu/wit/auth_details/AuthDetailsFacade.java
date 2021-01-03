package pl.edu.wit.auth_details;

import lombok.Getter;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.PasswordEncoder;
import pl.edu.wit.auth_details.port.primary.CreateAuthDetailsUseCase;
import pl.edu.wit.auth_details.port.primary.FindAuthDetailsUseCase;
import pl.edu.wit.auth_details.port.secondary.AuthDetailsRepository;

@Getter
public class AuthDetailsFacade {

    private final CreateAuthDetailsUseCase createAuthDetailsUseCase;
    private final FindAuthDetailsUseCase findAuthDetailsUseCase;

    public AuthDetailsFacade(IdGenerator idGenerator, PasswordEncoder passwordEncoder, AuthDetailsRepository repository) {
        createAuthDetailsUseCase = new DomainCreateAuthDetailsUseCase(idGenerator, passwordEncoder, repository);
        findAuthDetailsUseCase = new DomainFindAuthDetailsUseCase(repository);
    }

}
