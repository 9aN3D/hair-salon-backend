package pl.edu.wit.hairsalon.web.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        boolean last,
        long totalElements,
        int totalPages,
        boolean first,
        long numberOfElements,
        Sort sort,
        long size,
        long number
) {

    public static <T> PagedResponse<T> from(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.isLast(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.getNumberOfElements(),
                page.getSort(),
                page.getSize(),
                page.getNumber()
        );
    }

}
