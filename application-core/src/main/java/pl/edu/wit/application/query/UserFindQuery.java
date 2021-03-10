package pl.edu.wit.application.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFindQuery {

    private String authDetailsId;

    private String userId;

    public static UserFindQuery ofAuthDetailsId(String authDetailsId) {
        return UserFindQuery.builder()
                .authDetailsId(authDetailsId)
                .build();
    }

    public static UserFindQuery ofUserId(String userId) {
        return UserFindQuery.builder()
                .userId(userId)
                .build();
    }

}
