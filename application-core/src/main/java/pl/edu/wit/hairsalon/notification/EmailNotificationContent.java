package pl.edu.wit.hairsalon.notification;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.notification.dto.EmailNotificationContentDto;
import pl.edu.wit.hairsalon.sharedkernel.domain.NotBlankString;

import java.io.File;

@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"body", "attachments"})
class EmailNotificationContent implements NotificationContent {

    private final String to;
    private final String subject;
    private final String body;
    private final boolean html;
    private final File[] attachments;

    public EmailNotificationContent(EmailNotificationContentDto content) {
        this(
                content.getTo(),
                content.getSubject(),
                content.getBody(),
                content.isHtml(),
                content.getAttachments()
        );
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
    public EmailNotificationContent validate() {
        validate(new NotBlankString(to), new NotBlankString(subject), new NotBlankString(body));
        return this;
    }

    public EmailNotificationContentDto toDto() {
        return EmailNotificationContentDto.builder()
                .to(to)
                .subject(subject)
                .body(body)
                .html(html)
                .attachments(attachments)
                .build();
    }

}
