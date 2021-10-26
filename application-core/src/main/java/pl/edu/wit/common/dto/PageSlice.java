package pl.edu.wit.common.dto;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Value
public class PageSlice<T> {

    List<T> content = new ArrayList<>();

    Boolean last;

    Long totalElements;

    Integer totalPages;

    Boolean first;

    PageSliceSort sort;

    Integer size;

    Integer number;

    public PageSlice(List<T> content,
                     Boolean last,
                     Long totalElements,
                     Integer totalPages,
                     Boolean first,
                     PageSliceSort sort,
                     Integer size,
                     Integer number) {
        this.content.addAll(content);
        this.last = last;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.sort = sort;
        this.size = size;
        this.number = number;
    }

    public <U> PageSlice<U> map(Function<? super T, ? extends U> converter) {
        return new PageSlice<>(
                convertContent(converter),
                last,
                totalElements,
                totalPages,
                first,
                sort,
                size,
                number
        );
    }

    public Boolean hasContent() {
        return !content.isEmpty();
    }

    private <U> List<U> convertContent(Function<? super T, ? extends U> converter) {
        return content.stream()
                .map(converter)
                .collect(toList());
    }

}
