package pl.edu.wit.hairsalon.web.response;

import jakarta.validation.constraints.NotBlank;

/**
 * Odpowiedź zawierająca link do dodania wizyty do kalendarza Google użytkownika.
 * <p>
 * Wartość {@code value} to pełny URL, który prowadzi do formularza tworzenia wydarzenia
 * w interfejsie Google Calendar, wypełnionego danymi wizyty (czas, lokalizacja, nazwa itp.).
 * </p>
 *
 * <p>Używana po rezerwacji wizyty, aby użytkownik mógł łatwo dodać ją do swojego kalendarza.</p>
 *
 * @param value URL dodający wydarzenie do Google Calendar (niepusty)
 */
public record LinkAddingGoogleCalendarEventResponse(
        @NotBlank String value
) {
    
}
