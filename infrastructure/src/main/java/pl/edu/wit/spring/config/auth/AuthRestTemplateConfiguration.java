package pl.edu.wit.spring.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.lang.NonNull;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AuthRestTemplateConfiguration {

    private final ResourceServerProperties resourceServerProperties;

    @Bean
    public RestTemplate restTemplate() {
        var restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(resourceServerProperties.getClientId(), resourceServerProperties.getClientSecret()));
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(@NonNull ClientHttpResponse clientHttpResponse) throws IOException {
                return (clientHttpResponse.getStatusCode().is4xxClientError() || clientHttpResponse.getStatusCode().is5xxServerError());
            }

            @Override
            public void handleError(@NonNull ClientHttpResponse clientHttpResponse) throws IOException {
                log.warn("Error response from auth-server {code: {}, status: {}}", clientHttpResponse.getStatusCode(), clientHttpResponse.getStatusText());
            }
        });
        restTemplate.setRequestFactory(prepareSimpleClientHttpRequestFactory());
        return restTemplate;
    }

    private SimpleClientHttpRequestFactory prepareSimpleClientHttpRequestFactory() {
        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        return requestFactory;
    }

}
