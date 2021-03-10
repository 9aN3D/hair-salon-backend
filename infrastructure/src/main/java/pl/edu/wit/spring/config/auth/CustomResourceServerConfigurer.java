package pl.edu.wit.spring.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class CustomResourceServerConfigurer implements ResourceServerConfigurer {

    private final ResourceServerProperties resourceServerProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resource) {
        resource.resourceId(resourceServerProperties.getResourceId());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests()
                .antMatchers(publicEndpoints()).permitAll()
                .antMatchers(adminEndpoints()).access("hasAnyRole('ADMIN')")
                .anyRequest().authenticated();
    }

    private String[] publicEndpoints() {
        return new String[]{
                "/oauth/token",
                "/api/v1/tokens/**",
                "/api/v1/registration/**"
        };
    }

    private String[] adminEndpoints() {
        return new String[]{
                "/api/v1/admin/**",
        };
    }

}
