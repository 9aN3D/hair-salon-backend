package pl.edu.wit.hairsalon.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import pl.edu.wit.hairsalon.setting.SettingPort;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.dto.SettingGroupDto;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_LOGIN;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_OUTCOMING;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_PASSWORD;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_REQUIRE_SECURE_CONNECTION;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_SMTP_HOST;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.NOTIFICATIONS_EMAIL_SMTP_PORT;

@Configuration
class MailSenderConfiguration {

    @Bean
    JavaMailSender javaMailSender(SettingPort settingPort) {
        var notificationSettings = getEmailNotificationSettings(settingPort);
        return initMailSender(notificationSettings);
    }

    private JavaMailSender initMailSender(Map<SettingIdDto, String> notificationSettings) {
        var mailSender = new JavaMailSenderImpl();
        if (notificationSettings.isEmpty()) {
            return mailSender;
        }
        setMailParams(notificationSettings, mailSender);
        setMailProperties(notificationSettings, mailSender);
        return mailSender;
    }

    private void setMailParams(Map<SettingIdDto, String> notificationSettings, JavaMailSenderImpl mailSender) {
        mailSender.setUsername(notificationSettings.get(NOTIFICATIONS_EMAIL_LOGIN));
        mailSender.setPassword(notificationSettings.get(NOTIFICATIONS_EMAIL_PASSWORD));
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(notificationSettings.get(NOTIFICATIONS_EMAIL_SMTP_HOST));
        mailSender.setPort(parseInt(notificationSettings.getOrDefault(NOTIFICATIONS_EMAIL_SMTP_PORT, "25")));
    }

    private void setMailProperties(Map<SettingIdDto, String> notificationSettings, JavaMailSenderImpl mailSender) {
        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", notificationSettings.get(NOTIFICATIONS_EMAIL_REQUIRE_SECURE_CONNECTION));
        props.put("mail.smtp.starttls.enable", notificationSettings.get(NOTIFICATIONS_EMAIL_REQUIRE_SECURE_CONNECTION));
        props.put("mail.smtp.from", notificationSettings.get(NOTIFICATIONS_EMAIL_OUTCOMING));
        props.put("mail.smtp.connectiontimeout", "30000");
        props.put("mail.smtp.timeout", "30000");
        props.put("mail.smtp.writetimeout", "30000");
        props.put("mail.debug", "true");
    }

    private Map<SettingIdDto, String> getEmailNotificationSettings(SettingPort settingPort) {
        return settingPort.findAll(SettingGroupDto.NOTIFICATION_EMAIL).stream()
                .collect(Collectors.toMap(SettingDto::getId, SettingDto::getValue));
    }

}
