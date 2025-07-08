package pl.edu.wit.hairsalon.web.response;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje szczegóły problemu w odpowiedzi HTTP, zgodnie ze specyfikacją RFC 7807.
 * <p>
 * Obiekt typu {@code Problem} służy do przekazywania informacji o błędach w spójny i czytelny sposób
 * klientowi API. Może zawierać m.in. tytuł błędu, kod statusu HTTP, szczegółowy opis oraz listę naruszeń walidacyjnych.
 * </p>
 *
 * <p>Przykładowa odpowiedź:</p>
 * <pre>
 * {
 *   "title": "Invalid request",
 *   "status": 400,
 *   "detail": "Missing required field: email",
 *   "violations": [
 *     {
 *       "field": "email",
 *       "message": "must not be blank"
 *     }
 *   ]
 * }
 * </pre>
 *
 * <p>Obiekt może być tworzony bezpośrednio lub przez {@link Builder}.</p>
 *
 * @param title      tytuł błędu (np. "Invalid request")
 * @param status     status HTTP odpowiadający problemowi
 * @param detail     szczegółowy opis błędu
 * @param violations lista naruszeń walidacyjnych (może być pusta, nigdy null)
 *
 * @see Violation
 * @see org.springframework.http.HttpStatus
 */
public record Problem(
        String title,
        HttpStatus status,
        String detail,
        List<Violation> violations
) {

    /**
     * Konstruktor zapewniający niemodyfikowalność listy naruszeń (tworzy kopię).
     *
     * @param title      tytuł błędu
     * @param status     status HTTP
     * @param detail     opis szczegółowy
     * @param violations lista naruszeń walidacyjnych (może być null)
     */
    public Problem {
        violations = violations != null ? new ArrayList<>(violations) : new ArrayList<>();
    }

    /**
     * Tworzy nową instancję {@link Builder} do wygodnej budowy obiektów typu {@link Problem}.
     *
     * @return instancja buildera
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder klasy {@link Problem}, ułatwiający konstruowanie odpowiedzi błędu.
     */
    public static final class Builder {

        private String title;
        private HttpStatus status;
        private String detail;
        private List<Violation> violations;

        /**
         * Ustawia tytuł problemu.
         *
         * @param title tytuł
         * @return builder
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Ustawia status HTTP.
         *
         * @param status status
         * @return builder
         */
        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        /**
         * Ustawia szczegółowy opis problemu.
         *
         * @param detail opis
         * @return builder
         */
        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        /**
         * Ustawia listę naruszeń walidacyjnych.
         *
         * @param violations lista naruszeń (może być null)
         * @return builder
         */
        public Builder violations(List<Violation> violations) {
            this.violations = violations;
            return this;
        }

        /**
         * Tworzy nową instancję {@link Problem} na podstawie ustawionych pól.
         *
         * @return obiekt problemu
         */
        public Problem build() {
            return new Problem(title, status, detail, violations);
        }

    }

}

