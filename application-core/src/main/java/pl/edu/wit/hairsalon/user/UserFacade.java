package pl.edu.wit.hairsalon.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.user.command.UserCreateCommand;
import pl.edu.wit.hairsalon.user.command.UserUpdateCommand;
import pl.edu.wit.hairsalon.user.dto.UserDto;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;

public interface UserFacade {

    String create(UserCreateCommand command);

    void update(String userId, UserUpdateCommand command);

    UserDto findOne(String authDetailsId);

    Page<UserDto> findAll(UserFindQuery findQuery, Pageable pageable);

}
