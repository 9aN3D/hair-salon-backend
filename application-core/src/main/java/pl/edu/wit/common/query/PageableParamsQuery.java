package pl.edu.wit.common.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.common.dto.PageSliceSort;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableParamsQuery {

    private Integer page;

    private Integer size;

    private PageSliceSort sort;

    public static PageableParamsQuery maxPageableParamsQuery() {
        return PageableParamsQuery.builder()
                .page(0)
                .size(Integer.MAX_VALUE)
                .sort(PageSliceSort.empty())
                .build();
    }

}
