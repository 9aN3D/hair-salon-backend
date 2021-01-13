package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.api.response.MemberResponse;
import pl.edu.wit.application.command.UpdateMemberCommand;
import pl.edu.wit.application.port.primary.MemberService;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members", produces = APPLICATION_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/{authDetailsId}")
    @ResponseStatus(OK)
    public MemberResponse get(@PathVariable String authDetailsId) {
        return MemberResponse.of(memberService.getOne(authDetailsId));
    }

    @PostMapping(value = "/{memberId}", consumes = APPLICATION_JSON_VALUE)
    public void update(@PathVariable String memberId, @NotNull @RequestBody UpdateMemberCommand command) {
        memberService.update(memberId, command);
    }

}
