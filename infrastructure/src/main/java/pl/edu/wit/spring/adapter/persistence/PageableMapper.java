package pl.edu.wit.spring.adapter.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.dto.PageSlice;
import pl.edu.wit.application.dto.PageSliceSort;
import pl.edu.wit.application.query.PageableParamsQuery;

import static java.util.Optional.ofNullable;

@Component
public class PageableMapper<U> {

    public Pageable toPageable(PageableParamsQuery paramsQuery) {
        return ofNullable(paramsQuery.getSort())
                .map(sort -> PageRequest.of(
                        paramsQuery.getPage(),
                        paramsQuery.getSize(),
                        Sort.Direction.fromString(paramsQuery.getSort().getDirection()),
                        paramsQuery.getSort().getOrder()))
                .orElseGet(() -> PageRequest.of(
                        paramsQuery.getPage(),
                        paramsQuery.getSize()));
    }

    public PageSlice<U> toPageSlice(Page<U> page) {
        return new PageSlice<U>(
                page.getContent(),
                page.isLast(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                toPageSliceSort(page.getSort()),
                page.getSize(),
                page.getNumber()
        );
    }

    private PageSliceSort toPageSliceSort(Sort sort) {
        if (sort.iterator().hasNext()) {
            var order = sort.iterator().next();
            return new PageSliceSort(
                    order.getProperty(),
                    order.getDirection().name()
            );
        }
        return PageSliceSort.empty();
    }

}
