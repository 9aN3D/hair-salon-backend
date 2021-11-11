package pl.edu.wit.hairsalon.servicecategory;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.servicecategory.exception.ServiceCategoryNotFoundException;
import pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
class MongoServiceCategoryAdapter implements ServiceCategoryPort {

    private final MongoServiceCategoryRepository repository;
    private final ServiceCategoryMapper mapper;

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
        var builder = new BooleanBuilder();
        findQuery.ifServiceCategoryIdPresent(productCategoryId -> builder.and(qServiceCategory.id.eq(productCategoryId)));
        findQuery.ifNamePresent(name -> builder.and(qServiceCategory.name.like(name)));
        findQuery.ifStatusPresent(status -> builder.and(qServiceCategory.status.eq(status)));
        findQuery.ifServiceIdsPresent(serviceIds -> builder.and(qServiceCategory.serviceIds.any().in(serviceIds)));
        findQuery.ifOrderPresent(order -> builder.and(qServiceCategory.order.eq(order)));
        return ofNullable(builder.getValue());
    }

}
