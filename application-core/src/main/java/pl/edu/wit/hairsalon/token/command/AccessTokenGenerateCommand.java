package pl.edu.wit.hairsalon.token.command;

import java.util.Objects;
import java.util.StringJoiner;

public record AccessTokenGenerateCommand(String email, String password) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AccessTokenGenerateCommand that)) return false;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccessTokenGenerateCommand.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .toString();
    }

}
