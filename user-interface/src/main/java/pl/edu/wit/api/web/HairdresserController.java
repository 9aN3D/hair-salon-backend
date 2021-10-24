package pl.edu.wit.api.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wit.api.facade.HairdresserFacade;
import pl.edu.wit.api.response.HairdresserResponse;
import pl.edu.wit.application.command.hairdresser.HairdresserCreateCommand;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.query.HairdresserFindQuery;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class HairdresserController {

    private final HairdresserFacade hairdresserFacade;

    @PostMapping(value = "/admin/hairdressers", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public String create(@NotNull @RequestBody HairdresserCreateCommand command) {
        return hairdresserFacade.create(command);
    }

    @PostMapping(value = "/admin/hairdressers/{hairdresserId}", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    public void uploadPhoto(@PathVariable String hairdresserId, @RequestPart MultipartFile file) {
        hairdresserFacade.uploadPhoto(hairdresserId, file);
    }

    @GetMapping(value = "/hairdressers/{hairdresserId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public HairdresserResponse findOne(@PathVariable String hairdresserId) {
        return hairdresserFacade.findOne(hairdresserId);
    }

    @GetMapping(value = "/hairdressers", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public PageSlice<HairdresserResponse> findAll(@NotNull HairdresserFindQuery findQuery, @NotNull Pageable pageable) {
        return hairdresserFacade.findAll(findQuery, pageable);
    }

}
