package pl.edu.wit.spring.adapter.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wit.application.port.secondary.AuthDetailsDao;
import pl.edu.wit.domain.dto.AuthDetailsDto;
import pl.edu.wit.domain.model.auth_details.AuthDetails;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorizedUserDetailsService implements UserDetailsService {

    private final AuthDetailsDao repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findOne(username)
                .map(AuthDetails::toDto)
                .map(this::checkStatus)
                .map(this::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(
                        format("Cannot find user by username: {%s}", username)));
    }

    private AuthDetailsDto checkStatus(AuthDetailsDto authDetailsDto) {
        switch (authDetailsDto.getStatus()) {
            case BANNED:
                throw new UserBannedException("User is banned by administrator");
            case NOT_ACTIVE:
                throw new UserNotActiveException("User is not active yet");
            default:
                return authDetailsDto;
        }
    }

    private UserDetails toUserDetails(AuthDetailsDto dto) {
        return User.builder()
                .username(dto.getEmail())
                .password(dto.getPassword())
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities(dto.getRole().toString())
                .build();
    }

    static class UserBannedException extends AuthenticationException {

        public UserBannedException(String message) {
            super(message);
        }

    }

    static class UserNotActiveException extends AuthenticationException {

        public UserNotActiveException(String message) {
            super(message);
        }

    }

}
