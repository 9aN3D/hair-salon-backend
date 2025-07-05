package pl.edu.wit.hairsalon.user.query;

public record UserFindQuery(
        String id,
        String fullName
) {

    public static UserFindQuery fullName(String fullName) {
        return new UserFindQuery(null, fullName);
    }

    public static UserFindQuery withId(String userId) {
        return new UserFindQuery(userId, null);
    }

}
