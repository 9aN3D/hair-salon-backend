package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.dto.Identity;
import pl.edu.wit.hairsalon.web.adapter.MemberResponseAdapter;
import pl.edu.wit.hairsalon.web.response.MemberResponse;

import jakarta.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "hair-salon-API")
class MemberController {

    private final MemberResponseAdapter memberResponseAdapter;

    @GetMapping(value = "/members")
    @ResponseStatus(OK)
    MemberResponse findOne(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity) {
        return memberResponseAdapter.findOne(identity.getId());
    }

    @PostMapping(value = "/members", consumes = APPLICATION_JSON_VALUE)
    void update(@Parameter(hidden = true) @AuthenticationPrincipal Identity identity, @NotNull @RequestBody MemberUpdateCommand command) {
        memberResponseAdapter.update(identity.getId(), command);
    }

    @GetMapping(value = "/admin/members")
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    Page<MemberResponse> findAll(@NotNull MemberFindQuery findQuery, @NotNull Pageable pageable) {
        return memberResponseAdapter.findAll(findQuery, pageable);
    }

}
