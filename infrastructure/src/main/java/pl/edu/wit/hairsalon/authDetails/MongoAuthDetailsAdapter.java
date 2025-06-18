package pl.edu.wit.hairsalon.authDetails;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.authDetails.dto.AuthDetailsDto;
import pl.edu.wit.hairsalon.authDetails.exception.AuthDetailsNotFoundException;
import pl.edu.wit.hairsalon.authDetails.query.AuthDetailsFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;

import java.util.Optional;

import static java.lang.String.format;

@Repository
class MongoAuthDetailsAdapter implements AuthDetailsPort {

    private final MongoAuthDetailsRepository repository;
    private final AuthDetailsMapper mapper;

    MongoAuthDetailsAdapter(MongoAuthDetailsRepository repository, AuthDetailsMapper authDetailsMapper) {
        this.repository = repository;
        this.mapper = authDetailsMapper;
    }

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
        return QuerydslPredicateBuilder.create()
                .equals(qAuthDetailsDocument.id, findQuery.id())
                .equals(qAuthDetailsDocument.email, findQuery.email())
                .build();
    }

}
