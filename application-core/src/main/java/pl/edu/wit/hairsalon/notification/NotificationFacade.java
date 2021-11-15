package pl.edu.wit.hairsalon.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.query.NotificationFindQuery;

public interface NotificationFacade {

    void send(NotificationSendCommand command);

    Page<NotificationDto> findAll(NotificationFindQuery findQuery, Pageable pageable);

}
