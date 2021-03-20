package pl.edu.wit.spring.adapter.persistence.user;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import pl.edu.wit.application.domain.model.user.User;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.port.secondary.UserDao;
import pl.edu.wit.application.query.PageableParamsQuery;
import pl.edu.wit.application.query.UserFindQuery;
import pl.edu.wit.spring.adapter.persistence.PageableMapper;
import pl.edu.wit.spring.adapter.persistence.user.mapper.UserMapper;
import pl.edu.wit.spring.adapter.persistence.user.model.QUserDocument;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class MongoUserDao implements UserDao {

    private final MongoUserRepository userRepository;
    private final UserMapper userMapper;
    private final PageableMapper<User> pageableMapper;

    @Override
    public Optional<User> findOne(UserFindQuery query) {
        var qUser = QUserDocument.userDocument;
        var builder = new BooleanBuilder();
        ofNullable(query.getUserId()).ifPresent(userId -> builder.and(qUser.id.eq(userId)));
        ofNullable(query.getAuthDetailsId()).ifPresent(authDetailsId -> builder.and(qUser.authDetails.id.eq(authDetailsId)));

        return ofNullable(builder.getValue())
                .flatMap(userRepository::findOne)
                .map(userMapper::toDomain);
    }

    @Override
    public PageSlice<User> findAll(UserFindQuery findQuery, PageableParamsQuery paramsQuery) {
        var qUser = QUserDocument.userDocument;
        var builder = new BooleanBuilder();
        ofNullable(findQuery.getFullName())
                .map(String::trim)
                .filter(String::isBlank)
                .map(String::toLowerCase)
                .ifPresent(fullName -> builder.and(qUser.name.lower().like(fullName)).or(qUser.surname.lower().like(fullName)));

        var pageable = pageableMapper.toPageable(paramsQuery);
        var page = ofNullable(builder.getValue())
                .map(predicate -> userRepository.findAll(predicate, pageable))
                .orElseGet(() -> userRepository.findAll(pageable))
                .map(userMapper::toDomain);
        return pageableMapper.toPageSlice(page);
    }

}
