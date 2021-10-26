package pl.edu.wit.product.adapter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.dto.ProductCategoryDto;
import pl.edu.wit.product.port.secondary.ProductCategoryDao;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.product.query.ProductCategoryFindQuery;
import pl.edu.wit.common.adapter.PageableMapper;
import pl.edu.wit.product.adapter.mapper.ProductCategoryMapper;
import pl.edu.wit.product.adapter.model.QProductCategoryDocument;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Repository
@RequiredArgsConstructor
public class MongoProductCategoryDao implements ProductCategoryDao {

    private final MongoProductCategoryRepository repository;
    private final ProductCategoryMapper mapper;
    private final PageableMapper<ProductCategoryDto> pageableMapper;

    @Override
    public String save(ProductCategoryDto productCategory) {
        var productCategoryDocument = mapper.toDocument(productCategory);
        return repository.save(productCategoryDocument).getId();
    }

    @Override
    public Optional<ProductCategoryDto> findOne(ProductCategoryFindQuery query) {
        return ofNullable(buildPredicate(query))
                .flatMap(repository::findOne)
                .map(mapper::toDto);
    }

    @Override
    public PageSlice<ProductCategoryDto> findAll(ProductCategoryFindQuery findQuery, PageableParamsQuery pageableQuery) {
        var pageable = pageableMapper.toPageable(pageableQuery);
        var page = ofNullable(buildPredicate(findQuery))
                .map(predicate -> repository.findAll(predicate, pageable))
                .orElseGet(() -> repository.findAll(pageable))
                .map(mapper::toDto);
        return pageableMapper.toPageSlice(page);
    }

    private Predicate buildPredicate(ProductCategoryFindQuery findQuery) {
        var qProductCategory = QProductCategoryDocument.productCategoryDocument;
        var builder = new BooleanBuilder();
        ofNullable(findQuery.getProductCategoryId()).ifPresent(productCategoryId -> builder.and(qProductCategory.id.eq(productCategoryId)));
        buildLikeProductCategoryName(findQuery, qProductCategory, builder);
        return builder.getValue();
    }

    private void buildLikeProductCategoryName(ProductCategoryFindQuery findQuery, QProductCategoryDocument qProductCategory, BooleanBuilder builder) {
        ofNullable(findQuery.getName())
                .map(String::trim)
                .filter(not(String::isBlank))
                .ifPresent(name -> builder.and(qProductCategory.name.like(name)));
    }

}
