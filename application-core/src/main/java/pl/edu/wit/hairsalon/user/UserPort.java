package pl.edu.wit.hairsalon.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.user.dto.UserDto;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;

import java.util.Optional;

public interface UserPort {

    UserDto save(UserDto user);

    Optional<UserDto> findOne(UserFindQuery query);

    UserDto findOneOrThrow(UserFindQuery query);

    Page<UserDto> findAll(UserFindQuery findQuery, Pageable pageable);

}
