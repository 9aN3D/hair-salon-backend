package pl.edu.wit.hairsalon.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;
import pl.edu.wit.hairsalon.token.exception.InvalidCredentialsException;

import static java.lang.String.format;

@Service
class OAuth2AccessTokenAdapter implements AccessTokenPort {

    private final Logger log = LoggerFactory.getLogger(OAuth2AccessTokenAdapter.class);
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshService;

    public OAuth2AccessTokenAdapter(AuthenticationManager authManager, JwtTokenProvider jwtProvider,
                                    UserDetailsService userDetailsService, RefreshTokenService refreshService) {
        this.authManager = authManager;
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.refreshService = refreshService;
    }

    @Override
    public AccessTokenDto generate(String email, String password) {
        try {
            var authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            var user = (UserDetails) authentication.getPrincipal();
            var accessToken = jwtProvider.generateAccessToken(user);
            var refreshToken = jwtProvider.generateRefreshToken(user.getUsername());
            refreshService.saveRefreshToken(user.getUsername(), refreshToken);
            return new AccessTokenDto(accessToken, refreshToken);
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
        return new AccessTokenDto(jwtProvider.generateAccessToken(user), refreshToken);
    }

}
