package pl.edu.wit.hairsalon.token;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.util.StringUtils.hasText;

@EnableMethodSecurity
@EnableConfigurationProperties(SpringOAuthServerClientProperties.class)
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtFilter,
                                                   AuthenticationManager authManager,
                                                   AuthenticationEntryPoint jwtAuthenticationEntryPoint) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(publicEndpoints()).permitAll()
                        .requestMatchers(adminEndpoints()).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .authenticationManager(authManager)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             PasswordEncoder encoder,
                                             UserDetailsService userDetailsService) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return (request, response, authenticationException) -> {
            var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (hasText(authHeader) && authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Invalid or expired JWT token\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"error\": \"Access denied. Authentication is required.\"}");
            }
            response.setContentType("application/json");
        };
    }

    private String[] publicEndpoints() {
        return new String[]{
                "/public/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/api-docs/**",
                "/api/v1/tokens/**",
                "/api/v1/registration/**",
                "/api/v1/services/**",
                "/api/v1/files/**",
                "/api/v1/hairdressers/**",
                "/api/v1/settings/**",
                "/api/v1/scheduled-events"
        };
    }

    private String[] adminEndpoints() {
        return new String[]{
                "/api/v1/admin/**",
        };
    }

}
