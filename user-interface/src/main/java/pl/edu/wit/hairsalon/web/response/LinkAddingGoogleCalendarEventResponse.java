package pl.edu.wit.hairsalon.web.response;

import lombok.Value;

import jakarta.validation.constraints.NotBlank;

@Value
public class LinkAddingGoogleCalendarEventResponse {

    @NotBlank
    String value;

}
