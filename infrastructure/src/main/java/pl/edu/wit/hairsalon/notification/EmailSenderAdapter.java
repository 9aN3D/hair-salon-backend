package pl.edu.wit.hairsalon.notification;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.notification.dto.EmailNotificationContentDto;

@Service
class EmailSenderAdapter implements EmailSenderPort {

    private final Logger log = LoggerFactory.getLogger(EmailSenderAdapter.class);
    private final JavaMailSender javaMailSender;

    EmailSenderAdapter(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean send(EmailNotificationContentDto notificationContent) {
        return trySend(notificationContent);
    }

    private boolean trySend(EmailNotificationContentDto notificationContent) {
        try {
            var mail = javaMailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mail, true, "UTF-8");
            helper.setTo(notificationContent.getTo());
            helper.setSubject(notificationContent.subject());
            helper.setText(notificationContent.getBody(), notificationContent.html());
            javaMailSender.send(mail);
            return true;
        } catch (MessagingException exception) {
            log.error("Email send exception message: {}", exception.getMessage(), exception);
            return false;
        }
    }

}
