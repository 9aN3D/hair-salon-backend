package pl.edu.wit.application.port.primary;

import pl.edu.wit.application.dto.UserDto;

public interface UserService {

    UserDto getOne(String authDetailsId);

}
