package pl.edu.wit.spring.adapter.persistence.auth_details;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wit.application.domain.model.auth_details.AuthDetails;
import pl.edu.wit.application.port.secondary.AuthDetailsDao;
import pl.edu.wit.application.query.AuthDetailsFindQuery;
import pl.edu.wit.spring.adapter.persistence.auth_details.mapper.AuthDetailsMapper;
import pl.edu.wit.spring.adapter.persistence.auth_details.model.QAuthDetailsDocument;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class MongoAuthDetailsDao implements AuthDetailsDao {

    private final MongoAuthDetailsRepository repository;
    private final AuthDetailsMapper mapper;

    @Override
    public Optional<AuthDetails> findOne(AuthDetailsFindQuery query) {
        var qAuthDetailsDocument = new QAuthDetailsDocument("authDetails");
        var builder = new BooleanBuilder();
        ofNullable(query.getId()).ifPresent(id -> builder.and(qAuthDetailsDocument.id.eq(id)));
        ofNullable(query.getEmail()).ifPresent(email -> builder.and(qAuthDetailsDocument.email.eq(email)));

        return ofNullable(builder.getValue())
                .flatMap(repository::findOne)
                .map(mapper::toDomain);
    }

}
