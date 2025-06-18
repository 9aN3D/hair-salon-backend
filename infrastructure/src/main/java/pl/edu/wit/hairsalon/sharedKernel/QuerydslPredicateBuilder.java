package pl.edu.wit.hairsalon.sharedKernel;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.SetPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import pl.edu.wit.hairsalon.sharedKernel.document.QEmbeddableDateRange;
import pl.edu.wit.hairsalon.sharedKernel.domain.DateRange;
import pl.edu.wit.hairsalon.sharedKernel.dto.DateRangeDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;

public class QuerydslPredicateBuilder {

    private final BooleanBuilder builder = new BooleanBuilder();

    public static QuerydslPredicateBuilder create() {
        return new QuerydslPredicateBuilder();
    }

    public Optional<Predicate> build() {
        return Optional.ofNullable(builder.getValue());
    }

    public <V> QuerydslPredicateBuilder equals(SimpleExpression<V> path, V value) {
        return predicate(path::eq, value, () -> nonNull(value));
    }

    public <V> QuerydslPredicateBuilder anyIn(SetPath<V, ? extends SimpleExpression<? super V>> path, Collection<V> value) {
        return predicate(v -> path.any().in(v), value, () -> nonNull(value) && !value.isEmpty());
    }

    public <V> QuerydslPredicateBuilder in(SimpleExpression<V> path, Collection<V> value) {
        return predicate(path::in, value, () -> nonNull(value) && !value.isEmpty());
    }

    public <V> QuerydslPredicateBuilder notIn(SimpleExpression<V> path, Collection<V> value) {
        return predicate(path::notIn, value, () -> nonNull(value) && !value.isEmpty());
    }

    public QuerydslPredicateBuilder like(List<StringPath> paths, String value) {
        Function<String, Predicate> predicateFunction = v -> {
            var builder = new BooleanBuilder();
            for (var path : paths) {
                builder.or(path.likeIgnoreCase(v.toLowerCase()));
            }
            return builder.getValue();
        };
        return predicate(predicateFunction, value, () -> nonNull(value) && !value.isBlank());
    }

    public QuerydslPredicateBuilder like(StringPath path, String value) {
        return predicate(v -> path.likeIgnoreCase(v.toLowerCase()), value, () -> nonNull(value) && !value.isBlank());
    }

    public QuerydslPredicateBuilder includes(QEmbeddableDateRange qDateRange, DateRangeDto dateRange) {
        return predicate(includes(qDateRange), dateRange, () -> nonNull(dateRange) && new DateRange(dateRange).isNotEmpty());
    }

    public QuerydslPredicateBuilder overlaps(QEmbeddableDateRange qDateRange, DateRangeDto dateRange) {
        Function<DateRangeDto, Predicate> predicateFunction = value -> qDateRange.start.between(value.start(), value.end())
                .or(qDateRange.end.between(value.start(), value.end()))
                .or(includes(qDateRange).apply(dateRange));
        return predicate(predicateFunction, dateRange, () -> nonNull(dateRange) && new DateRange(dateRange).isNotEmpty());
    }

    public QuerydslPredicateBuilder startsWith(StringPath path, String value) {
        if (value != null && !value.isBlank()) {
            builder.and(path.toLowerCase().startsWith(value.toLowerCase()));
        }
        return this;
    }

    public <V extends Comparable<? super V>> QuerydslPredicateBuilder greaterThanEqual(DateTimePath<V> path, V value) {
        return predicate(path::goe, value, () -> nonNull(value));
    }

    public <V extends Comparable<? super V>> QuerydslPredicateBuilder greaterThan(DateTimePath<V> path, V value) {
        return predicate(path::gt, value, () -> nonNull(value));
    }

    public <V extends Comparable<? super V>> QuerydslPredicateBuilder lessThanEqual(DateTimePath<V> path, V value) {
        return predicate(path::loe, value, () -> nonNull(value));
    }

    public <V extends Comparable<? super V>> QuerydslPredicateBuilder lessThan(DateTimePath<V> path, V value) {
        return predicate(path::lt, value, () -> nonNull(value));
    }

    public <V> QuerydslPredicateBuilder predicate(Function<V, Predicate> predicateProvider, V value, Supplier<Boolean> canApply) {
        if (canApply.get()) {
            builder.and(predicateProvider.apply(value));
        }
        return this;
    }

    private Function<DateRangeDto, Predicate> includes(QEmbeddableDateRange qDateRange) {
        return v -> qDateRange.start.between(v.start(), v.end()).and(qDateRange.end.between(v.start(), v.end()));
    }

}

