package pl.edu.wit.hairsalon.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;
import pl.edu.wit.hairsalon.token.exception.InvalidCredentialsException;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
class OAuth2AccessTokenAdapter implements AccessTokenPort {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshService;

    @Override
    public AccessTokenDto generate(String email, String password) {
        try {
            var authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            var user = (UserDetails) authentication.getPrincipal(); //TODO
            //var user = userDetailsService.loadUserByUsername(email);
            var accessToken = jwtProvider.generateAccessToken(user);
            var refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
            refreshService.saveRefreshToken(user.getUsername(), refreshToken);

            return AccessTokenDto.builder()
                    .value(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception ex) {
            log.warn("An error has occurred authorizing user: { username: {}, reason: {} }", email, ex.getMessage(), ex);
            throw new InvalidCredentialsException(format("Unable to log user in: {username: %s}", email));
        }
    }

    @Override
    public AccessTokenDto refresh(String refreshToken) {
        if (!jwtProvider.isTokenValid(refreshToken)) {
            throw new InvalidCredentialsException("Unable to refresh token");
        }

        var username = jwtProvider.getUsernameFromToken(refreshToken);

        if (!refreshService.isValid(username, refreshToken)) {
            throw new InvalidCredentialsException("Unable to refresh token");
        }

        var user = userDetailsService.loadUserByUsername(username);
        return AccessTokenDto.builder()
                .value(jwtProvider.generateAccessToken(user))
                .refreshToken(refreshToken)
                .build();
    }

}
