package pl.edu.wit.hairsalon.web.response;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public record Problem(
        String title,
        HttpStatus status,
        String detail,
        List<Violation> violations
) {

    public Problem {
        violations = violations != null ? new ArrayList<>(violations) : new ArrayList<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String title;
        private HttpStatus status;
        private String detail;
        private List<Violation> violations;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder violations(List<Violation> violations) {
            this.violations = violations;
            return this;
        }

        public Problem build() {
            return new Problem(title, status, detail, violations);
        }

    }

}

