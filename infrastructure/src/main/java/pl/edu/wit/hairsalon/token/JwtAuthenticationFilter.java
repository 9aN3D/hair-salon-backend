package pl.edu.wit.hairsalon.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            resolveToken(request)
                    .filter(jwtProvider::isTokenValid)
                    .ifPresentOrElse(
                            token -> validateTokenAndSetAuthentication(token, new WebAuthenticationDetailsSource().buildDetails(request)),
                            () -> log.trace("No Authorization Bearer token found, skipping JWT filter")
                    );
        } catch (Exception e) {
            log.error("JwtAuthenticationFilter -> {}", e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        var bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return of(bearer.substring(7));
        }
        return empty();
    }

    private void validateTokenAndSetAuthentication(String token, WebAuthenticationDetails details) {
        var username = jwtProvider.getUsernameFromToken(token);
        var user = userDetailsService.loadUserByUsername(username);
        var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        auth.setDetails(details);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
