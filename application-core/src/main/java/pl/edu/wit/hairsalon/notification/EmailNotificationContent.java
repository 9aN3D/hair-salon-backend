package pl.edu.wit.hairsalon.notification;

import pl.edu.wit.hairsalon.notification.dto.EmailNotificationContentDto;
import pl.edu.wit.hairsalon.sharedKernel.domain.NotBlankString;

import java.io.File;
import java.util.Objects;
import java.util.StringJoiner;

record EmailNotificationContent(
        String to,
        String subject,
        String body,
        boolean html,
        File[] attachments
) implements NotificationContent {

    public EmailNotificationContent(EmailNotificationContentDto content) {
        this(
                content.getTo(),
                content.subject(),
                content.getBody(),
                content.html(),
                content.attachments()
        );
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmailNotificationContent that)) return false;
        return html == that.html && Objects.equals(to, that.to) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, subject, html);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EmailNotificationContent.class.getSimpleName() + "[", "]")
                .add("to='" + to + "'")
                .add("subject='" + subject + "'")
                .add("html=" + html)
                .toString();
    }

}
