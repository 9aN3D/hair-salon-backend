package pl.edu.wit.hairsalon.notification;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.smsapi.OAuthClient;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.exception.ClientException;
import pl.smsapi.proxy.ProxyNative;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SmsApiProperties.class)
class SmsSenderConfiguration {

    @Bean
    SmsFactory smsApi(SmsApiProperties smsApiProperties) throws ClientException {
        var client = new OAuthClient(smsApiProperties.getToken());
        var proxy = new ProxyNative(smsApiProperties.getProxyUrl());
        return new SmsFactory(client, proxy);
    }

}
