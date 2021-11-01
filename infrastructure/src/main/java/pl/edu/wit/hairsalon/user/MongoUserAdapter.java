package pl.edu.wit.hairsalon.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.user.dto.UserDto;
import pl.edu.wit.hairsalon.user.exception.UserNotFoundException;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
class MongoUserAdapter implements UserPort {

    private final MongoUserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto save(UserDto user) {
        var userDocument = userMapper.toDocument(user);
        return userMapper.toDto(userRepository.save(userDocument));
    }

    @Override
    public Optional<UserDto> findOne(UserFindQuery query) {
        return getOneFromRepository(query);
    }

    @Override
    public UserDto findOneOrThrow(UserFindQuery query) {
        return getOneFromRepository(query)
                .orElseThrow(() -> new UserNotFoundException(
                        format("User not found by query: %s", query)
                ));
    }

    @Override
    public Page<UserDto> findAll(UserFindQuery findQuery, Pageable pageable) {
        return buildPredicate(findQuery)
                .map(predicate -> userRepository.findAll(predicate, pageable))
                .orElseGet(() -> userRepository.findAll(pageable))
                .map(userMapper::toDto);
    }

    private Optional<UserDto> getOneFromRepository(UserFindQuery query) {
        return buildPredicate(query)
                .flatMap(userRepository::findOne)
                .map(userMapper::toDto);
    }

    private Optional<Predicate> buildPredicate(UserFindQuery findQuery) {
        var qUser = QUserDocument.userDocument;
        var builder = new BooleanBuilder();
        findQuery.ifIdPresent(id -> builder.and(qUser.id.eq(id)));
        findQuery.ifFullNamePresent(fullName -> builder
                .and(qUser.fullName.name.like(fullName))
                .or(qUser.fullName.surname.like(fullName)));
        return ofNullable(builder.getValue());
    }

}
