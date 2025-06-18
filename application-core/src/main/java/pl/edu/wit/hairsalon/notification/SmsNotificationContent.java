package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.dto.SmsNotificationContentDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.util.StringJoiner;

record SmsNotificationContent(String to, String body) implements NotificationContent {

    public SmsNotificationContent(SmsNotificationContentDto content) {
        this(content.getTo(), content.getBody());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SmsNotificationContent.class.getSimpleName() + "[", "]")
                .add("to='" + to + "'")
                .toString();
    }

    @Override
    public String to() {
        return to;
    }

    @Override
    public String body() {
        return body;
    }

    @Override
    public SmsNotificationContent validate() {
        validate(new NotBlankString(to), new NotBlankString(body));
        return this;
    }

    public SmsNotificationContentDto toDto() {
        return new SmsNotificationContentDto(
                to,
                body
        );
    }

}
