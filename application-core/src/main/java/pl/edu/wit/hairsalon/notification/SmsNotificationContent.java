package pl.edu.wit.hairsalon.notification;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.notification.dto.SmsNotificationContentDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "body")
class SmsNotificationContent implements NotificationContent {

    private final String to;
    private final String body;

    public SmsNotificationContent(SmsNotificationContentDto content) {
        this(content.getTo(), content.getBody());
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
        return SmsNotificationContentDto.builder()
                .to(to)
                .body(body)
                .build();
    }

}
