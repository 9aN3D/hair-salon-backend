package pl.edu.wit.application.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.application.dto.PageSliceSort;

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
