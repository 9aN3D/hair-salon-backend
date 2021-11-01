package pl.edu.wit.hairsalon.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.edu.wit.hairsalon.token.dto.AccessTokenDto;
import pl.edu.wit.hairsalon.token.exception.InvalidCredentialsException;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
class OAuth2AccessTokenAdapter implements AccessTokenPort {

    private final RestTemplate restTemplate;
    private final ResourceServerProperties resourceServerProperties;

    @Override
    public AccessTokenDto generate(String email, String password) {
        var loginMap = prepareGenerateRequestEntityBody(email, password);
        var headers = prepareHeaders();
        var requestEntity = new HttpEntity<>(loginMap, headers);
        var responseEntity = exchange(requestEntity);
        return handleGenerateTokenResponse(responseEntity, email);
    }

    @Override
    public AccessTokenDto refresh(String refreshToken) {
        var loginMap = prepareRefreshRequestEntityBody(refreshToken);
        var headers = prepareHeaders();
        var requestEntity = new HttpEntity<>(loginMap, headers);
        var responseEntity = exchange(requestEntity);
        return handleRefreshTokenResponse(responseEntity);
    }

    private MultiValueMap<String, String> prepareGenerateRequestEntityBody(String email, String password) {
        return new LinkedMultiValueMap<>() {{
            put("grant_type", List.of("password"));
            put("username", List.of(email));
            put("password", List.of(password));
        }};
    }

    private HttpHeaders prepareHeaders() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private ResponseEntity<Map> exchange(HttpEntity<MultiValueMap<String, String>> requestEntity) {
        return restTemplate.exchange(resourceServerProperties.getTokenInfoUri(), HttpMethod.POST, requestEntity, Map.class);
    }

    private AccessTokenDto handleGenerateTokenResponse(ResponseEntity<Map> responseEntity, String email) {
        if (isNull(responseEntity) || isNull(responseEntity.getBody())) {
            return handleEmptyResponse(email);
        }
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Got access token: {username: {}}}", email);
            return toAccessTokenDto(responseEntity.getBody());
        }
        log.warn("Unable to log user {response: {}}", responseEntity.getBody());
        throw new InvalidCredentialsException(format("Unable to log in user: {username: %s}", email));
    }

    private AccessTokenDto handleEmptyResponse(String username) {
        var errorMessage = format("Unable to log in user: {username: %s}. Response body is empty", username);
        log.warn(errorMessage);
        throw new InvalidCredentialsException(errorMessage);
    }

    private AccessTokenDto toAccessTokenDto(Map body) {
        return AccessTokenDto.builder()
                .value((String) body.get("access_token"))
                .refreshToken((String) body.get("refresh_token"))
                .tokenType((String) body.get("token_type"))
                .expiresIn((Integer) body.get("expires_in"))
                .build();
    }

    private MultiValueMap<String, String> prepareRefreshRequestEntityBody(String refreshToken) {
        return new LinkedMultiValueMap<>() {{
            add("grant_type", "refresh_token");
            add("refresh_token", refreshToken);
        }};
    }

    private AccessTokenDto handleRefreshTokenResponse(ResponseEntity<Map> responseEntity) {
        if (isNull(responseEntity) || isNull(responseEntity.getBody())) {
            throw new InvalidCredentialsException("Unable to refresh token. Response body is empty");
        }
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Successful token refresh");
            return toAccessTokenDto(responseEntity.getBody());
        }
        log.warn("Unable to refresh token {response: {}}", responseEntity.getBody());
        throw new InvalidCredentialsException("Unable to refresh token");
    }

}
