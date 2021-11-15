package pl.edu.wit.hairsalon.notification.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SmsShipmentStatusDto {

    NOT_FOUND(NotificationStatusDto.ERROR, "Błędny numer ID lub raport wygasł"),
    EXPIRED(NotificationStatusDto.UNDELIVERED, "Wiadomość niedostarczona z powodu zbyt długiego czasu niedostępność numeru"),
    SENT(NotificationStatusDto.SENT, "Wiadomość została wysłana ale operator nie zwrócił jeszcze raportu doręczenia"),
    DELIVERED(NotificationStatusDto.DELIVERED, "Wiadomość dotarła do odbiorcy"),
    UNDELIVERED(NotificationStatusDto.UNDELIVERED, "Wiadomość niedostarczona (np.: błędny numer, numer niedostępny)"),
    FAILED(NotificationStatusDto.ERROR, "Błąd podczas wysyłki wiadomości"),
    REJECTED(NotificationStatusDto.UNDELIVERED, "Wiadomość niedostarczona (np.: błędny numer, numer niedostępny)"),
    UNKNOWN(NotificationStatusDto.ERROR, "Brak raportu doręczenia dla wiadomości (wiadomość doręczona lub brak możliwości doręczenia)"),
    QUEUE(NotificationStatusDto.SENT, "Wiadomość czeka w kolejce na wysyłkę"),
    ACCEPTED(NotificationStatusDto.SENT, "Wiadomość przyjęta przez operatora"),
    RENEWAL(NotificationStatusDto.SENT, "Wykonana była próba połączenia która nie została odebrana, połączenie zostanie ponowione."),
    STOP(NotificationStatusDto.SENT, "Zatrzymanie"),
    ERROR(NotificationStatusDto.ERROR, "-");

    private final NotificationStatusDto notificationStatus;
    private final String description;

}
