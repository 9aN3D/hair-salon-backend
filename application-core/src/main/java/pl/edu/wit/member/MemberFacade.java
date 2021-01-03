package pl.edu.wit.member;

import lombok.Getter;
import pl.edu.wit.IdGenerator;
import pl.edu.wit.auth_details.AuthDetailsFacade;
import pl.edu.wit.member.port.primary.RegisterMemberUseCase;
import pl.edu.wit.member.port.secondary.MemberRepository;

@Getter
public class MemberFacade {

    private final RegisterMemberUseCase registerMemberUseCase;

    public MemberFacade(IdGenerator idGenerator, MemberRepository repository, AuthDetailsFacade authDetailsFacade) {
        this.registerMemberUseCase = new DomainRegisterMemberUseCase(idGenerator, repository, authDetailsFacade.getCreateAuthDetailsUseCase());
    }

}
