package pl.edu.wit.user.adapter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.user.dto.UserDto;
import pl.edu.wit.user.port.secondary.UserDao;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.user.query.UserFindQuery;
import pl.edu.wit.common.adapter.PageableMapper;
import pl.edu.wit.user.adapter.mapper.UserMapper;
import pl.edu.wit.user.adapter.model.QUserDocument;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Repository
@RequiredArgsConstructor
public class MongoUserDao implements UserDao {

    private final MongoUserRepository userRepository;
    private final UserMapper userMapper;
    private final PageableMapper<UserDto> pageableMapper;

    @Override
    public Optional<UserDto> findOne(UserFindQuery query) {
        var qUser = QUserDocument.userDocument;
        var builder = new BooleanBuilder();
        ofNullable(query.getUserId()).ifPresent(userId -> builder.and(qUser.id.eq(userId)));
        ofNullable(query.getAuthDetailsId()).ifPresent(authDetailsId -> builder.and(qUser.authDetails.id.eq(authDetailsId)));

        return ofNullable(builder.getValue())
                .flatMap(userRepository::findOne)
                .map(userMapper::toDto);
    }

    @Override
    public PageSlice<UserDto> findAll(UserFindQuery findQuery, PageableParamsQuery paramsQuery) {
        var pageable = pageableMapper.toPageable(paramsQuery);
        var page = ofNullable(buildPredicate(findQuery))
                .map(predicate -> userRepository.findAll(predicate, pageable))
                .orElseGet(() -> userRepository.findAll(pageable))
                .map(userMapper::toDto);
        return pageableMapper.toPageSlice(page);
    }

    private Predicate buildPredicate(UserFindQuery findQuery) {
        var qUser = QUserDocument.userDocument;
        var builder = new BooleanBuilder();
        buildLikeFullName(findQuery, qUser, builder);
        return builder.getValue();
    }

    private void buildLikeFullName(UserFindQuery findQuery, QUserDocument qUser, BooleanBuilder builder) {
        ofNullable(findQuery.getFullName())
                .map(String::trim)
                .filter(not(String::isBlank))
                .map(String::toLowerCase)
                .ifPresent(fullName -> builder.and(qUser.name.lower().like(fullName)).or(qUser.surname.lower().like(fullName)));
    }

}
