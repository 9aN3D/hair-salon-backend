package pl.edu.wit.hairsalon.authDetails;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsRoleDto;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsStatusDto;

import java.util.Objects;

@QueryEntity
@Document(value = "AuthDetails")
class AuthDetailsDocument {

    @Id
    private String id;

    @Indexed
    private String email;

    private String password;

    private AuthDetailsStatusDto status;

    private AuthDetailsRoleDto role;

    public AuthDetailsDocument() {
    }

    public AuthDetailsDocument(String id, String email,
                        String password, AuthDetailsStatusDto status,
                        AuthDetailsRoleDto role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthDetailsStatusDto getStatus() {
        return status;
    }

    public void setStatus(AuthDetailsStatusDto status) {
        this.status = status;
    }

    public AuthDetailsRoleDto getRole() {
        return role;
    }

    public void setRole(AuthDetailsRoleDto role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthDetailsDocument)) return false;
        AuthDetailsDocument that = (AuthDetailsDocument) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private String id;
        private String email;
        private String password;
        private AuthDetailsStatusDto status;
        private AuthDetailsRoleDto role;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder status(AuthDetailsStatusDto status) {
            this.status = status;
            return this;
        }

        public Builder role(AuthDetailsRoleDto role) {
            this.role = role;
            return this;
        }

        public AuthDetailsDocument build() {
            return new AuthDetailsDocument(id, email, password, status, role);
        }

    }

}

