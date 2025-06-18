package pl.edu.wit.hairsalon.notification.dto;

public record SmsNotificationContentDto(String to, String body) implements NotificationContentDto {

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public String getBody() {
        return body;
    }

}
