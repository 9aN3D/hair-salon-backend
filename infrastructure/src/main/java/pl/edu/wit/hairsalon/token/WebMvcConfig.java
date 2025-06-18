package pl.edu.wit.hairsalon.token;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class WebMvcConfig implements WebMvcConfigurer {

    private final IdentityMethodArgumentResolver identityMethodArgumentResolver;

    WebMvcConfig(IdentityMethodArgumentResolver identityMethodArgumentResolver) {
        this.identityMethodArgumentResolver = identityMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(identityMethodArgumentResolver);
    }

}
