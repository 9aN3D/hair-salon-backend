package pl.edu.wit.product.adapter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.wit.common.dto.PageSlice;
import pl.edu.wit.product.dto.ProductDto;
import pl.edu.wit.product.port.secondary.ProductDao;
import pl.edu.wit.common.query.PageableParamsQuery;
import pl.edu.wit.product.query.ProductFindQuery;
import pl.edu.wit.common.adapter.PageableMapper;
import pl.edu.wit.product.adapter.mapper.ProductMapper;
import pl.edu.wit.product.adapter.model.QProductDocument;

import java.util.Optional;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static java.util.function.Predicate.not;

@Repository
@RequiredArgsConstructor
public class MongoProductDao implements ProductDao {

    private final MongoProductRepository repository;
    private final ProductMapper mapper;
    private final PageableMapper<ProductDto> pageableMapper;

    @Override
    public String save(ProductDto product) {
        var productDocument = mapper.toDocument(product);
        return repository.save(productDocument).getId();
    }

    @Override
    public Optional<ProductDto> findOne(ProductFindQuery findQuery) {
        return ofNullable(buildPredicate(findQuery))
                .flatMap(repository::findOne)
                .map(mapper::toDto);
    }

    @Override
    public PageSlice<ProductDto> findAll(ProductFindQuery findQuery, PageableParamsQuery pageableQuery) {
        var pageable = pageableMapper.toPageable(pageableQuery);
        var page = ofNullable(buildPredicate(findQuery))
                .map(predicate -> repository.findAll(predicate, pageable))
                .orElseGet(() -> repository.findAll(pageable))
                .map(mapper::toDto);
        return pageableMapper.toPageSlice(page);
    }

    private Predicate buildPredicate(ProductFindQuery findQuery) {
        var qProduct = QProductDocument.productDocument;
        var builder = new BooleanBuilder();
        ofNullable(findQuery.getProductId()).ifPresent(productId -> builder.and(qProduct.id.eq(productId)));
        ofNullable(findQuery.getProductCategoryId()).ifPresent(productCategoryId -> builder.and(qProduct.category.id.eq(productCategoryId)));
        ofNullable(findQuery.getProductCategoryIds())
                .filter(not(Set::isEmpty))
                .ifPresent(categoryIds -> builder.and(qProduct.category.id.in(categoryIds)));
        ofNullable(findQuery.getProductIds())
                .filter(not(Set::isEmpty))
                .ifPresent(productIds -> builder.and(qProduct.id.in(productIds)));
        buildLikeProductName(findQuery, qProduct, builder);
        return builder.getValue();
    }

    private void buildLikeProductName(ProductFindQuery findQuery, QProductDocument qProductDocument, BooleanBuilder builder) {
        ofNullable(findQuery.getName())
                .map(String::trim)
                .filter(not(String::isBlank))
                .map(String::toLowerCase)
                .ifPresent(name -> builder.and(qProductDocument.name.lower().like(name)));
    }

}
