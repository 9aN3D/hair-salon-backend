package pl.edu.wit.hairsalon.notification;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;
import java.util.StringJoiner;

@ConfigurationProperties(prefix = "pl.edu.wit.smsapi")
class SmsApiProperties {

    @NotBlank
    private String token;

    @NotBlank
    private String proxyUrl;

    public SmsApiProperties() {
    }

    public SmsApiProperties(String token, String proxyUrl) {
        this.token = token;
        this.proxyUrl = proxyUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SmsApiProperties that)) return false;
        return Objects.equals(token, that.token) && Objects.equals(proxyUrl, that.proxyUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, proxyUrl);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SmsApiProperties.class.getSimpleName() + "[", "]")
                .add("token='" + token + "'")
                .add("proxyUrl='" + proxyUrl + "'")
                .toString();
    }

}
