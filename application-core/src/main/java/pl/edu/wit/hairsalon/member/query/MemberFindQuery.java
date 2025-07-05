package pl.edu.wit.hairsalon.member.query;

public record MemberFindQuery(
        String id,
        String fullName,
        String phone
) {

    public static MemberFindQuery withId(String id) {
        return new MemberFindQuery(id, null, null);
    }

}
