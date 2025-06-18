package pl.edu.wit.hairsalon.notification.dto;

import java.io.File;
import java.util.StringJoiner;

public record EmailNotificationContentDto(
        String to,
        String subject,
        String body,
        boolean html,
        File[] attachments  
) implements NotificationContentDto {

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EmailNotificationContentDto.class.getSimpleName() + "[", "]")
                .add("html=" + html)
                .add("subject='" + subject + "'")
                .add("to='" + to + "'")
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String to;
        private String subject;
        private String body;
        private boolean html;
        private File[] attachments;

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder html(boolean html) {
            this.html = html;
            return this;
        }

        public Builder attachments(File[] attachments) {
            this.attachments = attachments;
            return this;
        }

        public EmailNotificationContentDto build() {
            return new EmailNotificationContentDto(
                    to,
                    subject,
                    body,
                    html,
                    attachments
            );
        }
    }

}
