package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
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
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.web.adapter.HairdresserResponseAdapter;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
class HairdresserController {

    private final HairdresserResponseAdapter hairdresserResponseAdapter;

    HairdresserController(HairdresserResponseAdapter hairdresserResponseAdapter) {
        this.hairdresserResponseAdapter = hairdresserResponseAdapter;
    }

    @PostMapping(value = "/admin/hairdressers", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    String create(@NotNull @RequestBody HairdresserCreateCommand command) {
        return hairdresserResponseAdapter.create(command);
    }

    @PostMapping(value = "/admin/hairdressers/{hairdresserId}", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    @SecurityRequirement(name = "hair-salon-API")
    void uploadPhoto(@PathVariable String hairdresserId, @RequestPart MultipartFile file) {
        hairdresserResponseAdapter.uploadPhoto(hairdresserId, file);
    }

    @GetMapping(value = "/hairdressers/{hairdresserId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    HairdresserResponse findOne(@PathVariable String hairdresserId) {
        return hairdresserResponseAdapter.findOne(hairdresserId);
    }

    @GetMapping(value = "/hairdressers", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    Page<HairdresserResponse> findAll(@NotNull HairdresserFindQuery findQuery, @NotNull Pageable pageable) {
        return hairdresserResponseAdapter.findAll(findQuery, pageable);
    }

}
