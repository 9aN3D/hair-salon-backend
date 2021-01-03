package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.member.MemberFacade;
import pl.edu.wit.member.shared.command.RegisterMemberCommand;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final MemberFacade memberFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public void register(@RequestBody RegisterMemberCommand command) {
        memberFacade.getRegisterMemberUseCase().register(command);
    }

}
