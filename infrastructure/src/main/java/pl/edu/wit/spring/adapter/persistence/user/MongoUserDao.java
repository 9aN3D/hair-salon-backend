package pl.edu.wit.spring.adapter.persistence.user;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.application.domain.model.user.User;
import pl.edu.wit.application.port.secondary.UserDao;
import pl.edu.wit.application.query.UserFindQuery;
import pl.edu.wit.spring.adapter.persistence.user.mapper.UserMapper;
import pl.edu.wit.spring.adapter.persistence.user.model.QUserDocument;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class MongoUserDao implements UserDao {

    private final MongoUserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findOne(UserFindQuery query) {
        var qUser = new QUserDocument("user");
        var builder = new BooleanBuilder();
        ofNullable(query.getUserId()).ifPresent(userId -> builder.and(qUser.id.eq(userId)));
        ofNullable(query.getAuthDetailsId()).ifPresent(authDetailsId -> builder.and(qUser.authDetails.id.eq(authDetailsId)));

        return ofNullable(builder.getValue())
                .flatMap(userRepository::findOne)
                .map(userMapper::toDomain);
    }

}
