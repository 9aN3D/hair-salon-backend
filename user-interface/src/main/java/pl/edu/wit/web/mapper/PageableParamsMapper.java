package pl.edu.wit.web.mapper;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pl.edu.wit.common.dto.PageSliceSort;
import pl.edu.wit.common.query.PageableParamsQuery;

@Component
public class PageableParamsMapper {

    public PageableParamsQuery toPageableParamsQuery(Pageable pageable) {
        return PageableParamsQuery.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .sort(toPageSliceSort(pageable.getSort()))
                .build();
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
