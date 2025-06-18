package pl.edu.wit.hairsalon.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wit.hairsalon.setting.SettingFacade;
import pl.edu.wit.hairsalon.setting.command.SettingCreateCommand;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;
import pl.edu.wit.hairsalon.setting.query.SettingGroupFindQuery;

import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
class SettingController {

    private final SettingFacade settingFacade;

    SettingController(SettingFacade settingFacade) {
        this.settingFacade = settingFacade;
    }

    @SecurityRequirement(name = "hair-salon-API")
    @PostMapping(value = "/admin/settings", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    void create(@NotNull @RequestBody SettingCreateCommand command) {
        settingFacade.create(command);
    }

    @SecurityRequirement(name = "hair-salon-API")
    @PutMapping(value = "/admin/settings/{settingId}")
    @ResponseStatus(NO_CONTENT)
    void update(@PathVariable SettingIdDto settingId, @NotNull @RequestBody String value) {
        settingFacade.update(settingId, value);
    }

    @GetMapping(value = "/settings/{settingId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    SettingDto findOne(@PathVariable SettingIdDto settingId) {
        return settingFacade.findOne(settingId);
    }

    @GetMapping(value = "/settings", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    Map<SettingIdDto, String> findAll(@NotNull SettingGroupFindQuery findQuery) {
        return settingFacade.findAll(findQuery).stream()
                .collect(toMap(SettingDto::id, SettingDto::value));
    }

}
