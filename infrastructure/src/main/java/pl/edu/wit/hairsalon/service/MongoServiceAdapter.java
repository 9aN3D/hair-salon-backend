package pl.edu.wit.hairsalon.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.exception.ServiceNotFoundException;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;

import java.util.Optional;

import static java.lang.String.format;

@Repository
class MongoServiceAdapter implements ServicePort {

    private final MongoServiceRepository repository;
    private final ServiceMapper mapper;

    MongoServiceAdapter(MongoServiceRepository repository, ServiceMapper serviceMapper) {
        this.repository = repository;
        this.mapper = serviceMapper;
    }

    @Override
    public String save(ServiceDto product) {
        var serviceDocument = mapper.toDocument(product);
        return repository.save(serviceDocument).getId();
    }

    @Override
    public Optional<ServiceDto> findOne(ServiceFindQuery findQuery) {
        return getOneFromRepository(findQuery);
    }

    @Override
    public ServiceDto findOneOrThrow(ServiceFindQuery findQuery) {
        return getOneFromRepository(findQuery)
                .orElseThrow(() -> new ServiceNotFoundException(
                        format("Service not found by findQuery: %s", findQuery))
                );
    }

    @Override
    public Page<ServiceDto> findAll(ServiceFindQuery findQuery, Pageable pageable) {
        return buildPredicate(findQuery)
                .map(predicate -> repository.findAll(predicate, pageable))
                .orElseGet(() -> repository.findAll(pageable))
                .map(mapper::toDto);
    }

    @Override
    public long count(ServiceFindQuery findQuery) {
        return buildPredicate(findQuery)
                .map(repository::count)
                .orElseGet(repository::count);
    }

    private Optional<ServiceDto> getOneFromRepository(ServiceFindQuery findQuery) {
        return buildPredicate(findQuery)
                .flatMap(repository::findOne)
                .map(mapper::toDto);
    }

    private Optional<Predicate> buildPredicate(ServiceFindQuery findQuery) {
        var qService = QServiceDocument.serviceDocument;
        return QuerydslPredicateBuilder.create()
                .equals(qService.id, findQuery.id())
                .in(qService.id, findQuery.ids())
                .like(qService.name, findQuery.name())
                .build();
    }

}
