package pl.edu.wit.auth.details.adapter;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.wit.auth.details.dto.AuthDetailsDto;
import pl.edu.wit.auth.details.port.secondary.AuthDetailsDao;
import pl.edu.wit.auth.details.query.AuthDetailsFindQuery;
import pl.edu.wit.auth.details.adapter.mapper.AuthDetailsMapper;
import pl.edu.wit.auth.details.adapter.model.QAuthDetailsDocument;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class MongoAuthDetailsDao implements AuthDetailsDao {

    private final MongoAuthDetailsRepository repository;
    private final AuthDetailsMapper mapper;

    @Override
    public AuthDetailsDto save(AuthDetailsDto authDetails) {
        var authDetailsDocument = mapper.toDocument(authDetails);
        return mapper.toDto(repository.save(authDetailsDocument));
    }

    @Override
    public Optional<AuthDetailsDto> findOne(AuthDetailsFindQuery query) {
        var qAuthDetailsDocument = QAuthDetailsDocument.authDetailsDocument;
        var builder = new BooleanBuilder();
        ofNullable(query.getId()).ifPresent(id -> builder.and(qAuthDetailsDocument.id.eq(id)));
        ofNullable(query.getEmail()).ifPresent(email -> builder.and(qAuthDetailsDocument.email.eq(email)));

        return ofNullable(builder.getValue())
                .flatMap(repository::findOne)
                .map(mapper::toDto);
    }

}
