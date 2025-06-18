package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;

public record LinkAddingGoogleCalendarEventResponse(
        @NotBlank String value
) {
    
}
