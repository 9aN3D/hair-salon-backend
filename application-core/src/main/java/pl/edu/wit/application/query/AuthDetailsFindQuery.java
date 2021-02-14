package pl.edu.wit.application.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDetailsFindQuery {

    private String id;

    private String email;

    public static AuthDetailsFindQuery ofAuthDetailsId(String authDetailsId) {
        return AuthDetailsFindQuery.builder()
                .id(authDetailsId)
                .build();
    }

    public static AuthDetailsFindQuery ofEmail(String email) {
        return AuthDetailsFindQuery.builder()
                .email(email)
                .build();
    }

}
