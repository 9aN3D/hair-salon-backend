package pl.edu.wit.hairsalon.notification;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.notification.dto.NotificationDto;
import pl.edu.wit.hairsalon.notification.exception.NotificationNotFoundException;
import pl.edu.wit.hairsalon.notification.query.NotificationFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;

import java.util.Optional;

import static java.lang.String.format;

@Repository
class MongoNotificationAdapter implements NotificationPort {

    private final MongoNotificationRepository repository;
    private final NotificationMapper mapper;

    MongoNotificationAdapter(MongoNotificationRepository repository, NotificationMapper notificationMapper) {
        this.repository = repository;
        this.mapper = notificationMapper;
    }

    @Override
    public NotificationDto save(NotificationDto notification) {
        var notificationDocument = mapper.toDocument(notification);
        return mapper.toDto(repository.save(notificationDocument));
    }

    @Override
    public NotificationDto findOneOrThrow(String notificationId) {
        return repository.findById(notificationId)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotificationNotFoundException(
                        format("Notification not found by id %s", notificationId)
                ));
    }

    @Override
    public Page<NotificationDto> findAll(NotificationFindQuery findQuery, Pageable pageable) {
        return buildPredicate(findQuery)
                .map(predicate -> repository.findAll(predicate, pageable))
                .orElseGet(() -> repository.findAll(pageable))
                .map(mapper::toDto);
    }

    private Optional<Predicate> buildPredicate(NotificationFindQuery findQuery) {
        var qNotificationDocument = QNotificationDocument.notificationDocument;
        return QuerydslPredicateBuilder.create()
                .equals(qNotificationDocument.recipientId, findQuery.recipientId())
                .build();
    }

}
