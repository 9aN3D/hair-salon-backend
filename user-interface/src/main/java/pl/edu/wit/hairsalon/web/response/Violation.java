package pl.edu.wit.hairsalon.web.response;

/**
 * Reprezentuje pojedyncze naruszenie walidacyjne dla pola w żądaniu użytkownika.
 * <p>
 * Używane jako część odpowiedzi typu {@code application/problem+json}, np. w polu
 * {@code violations} obiektu {@link Problem}.
 * </p>
 *
 * <p>Przykład naruszenia: </p>
 * <pre>
 * {
 *   "field": "email",
 *   "message": "must not be blank"
 * }
 * </pre>
 *
 * @param field   nazwa pola, którego dotyczy naruszenie (np. "email", "phoneNumber")
 * @param message komunikat błędu dla użytkownika (np. "must not be blank")
 *
 * @see Problem
 */
public record Violation(String field, String message) {

}
