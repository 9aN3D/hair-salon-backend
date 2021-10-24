package pl.edu.wit.api.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.api.facade.MemberFacade;
import pl.edu.wit.api.response.MemberResponse;
import pl.edu.wit.application.command.member.MemberUpdateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.MemberFindQuery;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
public class MemberController {

    private final MemberFacade memberFacade;

    @GetMapping(value = "/members/{authDetailsId}")
    @ResponseStatus(OK)
    public MemberResponse findOne(@PathVariable String authDetailsId) {
        return memberFacade.findOne(authDetailsId);
    }

    @PostMapping(value = "/members/{memberId}", consumes = APPLICATION_JSON_VALUE)
    public void update(@PathVariable String memberId, @NotNull @RequestBody MemberUpdateCommand command) {
        memberFacade.update(memberId, command);
    }

    @GetMapping(value = "/admin/members")
    @ResponseStatus(OK)
    public PageSlice<MemberResponse> findAll(@NotNull MemberFindQuery findQuery, @NotNull Pageable pageable) {
        return memberFacade.findAll(findQuery, pageable);
    }

}
