package pl.edu.wit.hairsalon.authdetails;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.authdetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.authdetails.exception.AuthDetailsNotFoundException;
import pl.edu.wit.hairsalon.authdetails.query.AuthDetailsFindQuery;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
class MongoAuthDetailsAdapter implements AuthDetailsPort {

    private final MongoAuthDetailsRepository repository;
    private final AuthDetailsMapper mapper;

    @Override
    public AuthDetailsDto save(AuthDetailsDto authDetails) {
        var authDetailsDocument = mapper.toDocument(authDetails);
        return mapper.toDto(repository.save(authDetailsDocument));
    }

    @Override
    public Optional<AuthDetailsDto> findOne(AuthDetailsFindQuery query) {
        return getOneFromRepository(query);
    }

    @Override
    public AuthDetailsDto findOneOrThrow(AuthDetailsFindQuery findQuery) {
        return getOneFromRepository(findQuery)
                .orElseThrow(() -> new AuthDetailsNotFoundException(
                        format("Auth details not found by findQuery: %s", findQuery))
                );
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    private Optional<AuthDetailsDto> getOneFromRepository(AuthDetailsFindQuery query) {
        return buildPredicate(query)
                .flatMap(repository::findOne)
                .map(mapper::toDto);
    }

    private Optional<Predicate> buildPredicate(AuthDetailsFindQuery findQuery) {
        var qAuthDetailsDocument = QAuthDetailsDocument.authDetailsDocument;
        var builder = new BooleanBuilder();
        findQuery.ifIdPresent(id -> builder.and(qAuthDetailsDocument.id.eq(id)));
        findQuery.ifEmailPresent(email -> builder.and(qAuthDetailsDocument.email.eq(email)));
        return ofNullable(builder.getValue());
    }

}
