package pl.edu.wit.member.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberFindQuery {

    private String authDetailsId;

    private String memberId;

    private String fullName;

    private String phone;

    public static MemberFindQuery ofAuthDetailsId(String authDetailsId) {
        return MemberFindQuery.builder()
                .authDetailsId(authDetailsId)
                .build();
    }

    public static MemberFindQuery ofMemberId(String memberId) {
        return MemberFindQuery.builder()
                .memberId(memberId)
                .build();
    }

}
