package pl.edu.wit.hairsalon.serviceCategory;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.serviceCategory.exception.ServiceCategoryNotFoundException;
import pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.QuerydslPredicateBuilder;

import java.util.Optional;

import static java.lang.String.format;

@Repository
class MongoServiceCategoryAdapter implements ServiceCategoryPort {

    private final MongoServiceCategoryRepository repository;
    private final ServiceCategoryMapper mapper;

    MongoServiceCategoryAdapter(MongoServiceCategoryRepository repository,
                                ServiceCategoryMapper serviceCategoryMapper) {
        this.repository = repository;
        this.mapper = serviceCategoryMapper;
    }

    @Override
    public String save(ServiceCategoryDto productCategory) {
        var productCategoryDocument = mapper.toDocument(productCategory);
        return repository.save(productCategoryDocument).getId();
    }

    @Override
    public Optional<ServiceCategoryDto> findOne(ServiceCategoryFindQuery query) {
        return getOneFromRepository(query);
    }

    @Override
    public ServiceCategoryDto findOneOrThrow(ServiceCategoryFindQuery query) {
        return getOneFromRepository(query)
                .orElseThrow(() -> new ServiceCategoryNotFoundException(
                        format("Product category not found by id: %s", query))
                );
    }

    @Override
    public Page<ServiceCategoryDto> findAll(ServiceCategoryFindQuery findQuery, Pageable pageable) {
        return buildPredicate(findQuery)
                .map(predicate -> repository.findAll(predicate, pageable))
                .orElseGet(() -> repository.findAll(pageable))
                .map(mapper::toDto);
    }

    private Optional<ServiceCategoryDto> getOneFromRepository(ServiceCategoryFindQuery query) {
        return buildPredicate(query)
                .flatMap(repository::findOne)
                .map(mapper::toDto);
    }

    private Optional<Predicate> buildPredicate(ServiceCategoryFindQuery findQuery) {
        var qServiceCategory = QServiceCategoryDocument.serviceCategoryDocument;
        return QuerydslPredicateBuilder.create()
                .equals(qServiceCategory.id, findQuery.serviceCategoryId())
                .like(qServiceCategory.name, findQuery.name())
                .equals(qServiceCategory.status, findQuery.status())
                .anyIn(qServiceCategory.serviceIds, findQuery.serviceIds())
                .equals(qServiceCategory.order, findQuery.order())
                .build();
    }

}
