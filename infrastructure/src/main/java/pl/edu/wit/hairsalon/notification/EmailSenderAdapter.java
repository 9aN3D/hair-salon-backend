package pl.edu.wit.hairsalon.notification;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.notification.dto.EmailNotificationContentDto;

@Slf4j
@Service
@RequiredArgsConstructor
class EmailSenderAdapter implements EmailSenderPort {

    private final JavaMailSender javaMailSender;

    @Override
    public boolean send(EmailNotificationContentDto notificationContent) {
        return trySend(notificationContent);
    }

    private boolean trySend(EmailNotificationContentDto notificationContent) {
        try {
            var mail = javaMailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mail, true, "UTF-8");
            helper.setTo(notificationContent.getTo());
            helper.setSubject(notificationContent.getSubject());
            helper.setText(notificationContent.getBody(), notificationContent.isHtml());
            javaMailSender.send(mail);
            return true;
        } catch (MessagingException exception) {
            log.error("Email send exception message: {}", exception.getMessage(), exception);
            return false;
        }
    }

}
