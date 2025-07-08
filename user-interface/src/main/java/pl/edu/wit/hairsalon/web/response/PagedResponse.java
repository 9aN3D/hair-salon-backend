package pl.edu.wit.hairsalon.web.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Generyczna paginowana odpowiedź dla kolekcji danych w API.
 * <p>
 * Opakowuje dane z obiektu {@link org.springframework.data.domain.Page}, zapewniając
 * klientowi informacje o zawartości, liczbie stron, elementach na stronie,
 * kolejności sortowania i pozycjonowaniu wśród wszystkich wyników.
 * </p>
 *
 * @param <T> typ danych w liście wyników
 *
 * @param content          lista wyników na aktualnej stronie
 * @param last             czy bieżąca strona jest ostatnią
 * @param totalElements    całkowita liczba elementów we wszystkich stronach
 * @param totalPages       całkowita liczba dostępnych stron
 * @param first            czy bieżąca strona jest pierwszą
 * @param numberOfElements liczba elementów na bieżącej stronie
 * @param sort             informacje o sortowaniu wyników
 * @param size             żądana liczba elementów na stronę (page size)
 * @param number           numer bieżącej strony (0-based)
 *
 * @see org.springframework.data.domain.Page
 * @see org.springframework.data.domain.Sort
 */
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

    /**
     * Tworzy instancję {@link PagedResponse} na podstawie obiektu {@link org.springframework.data.domain.Page}.
     *
     * @param page obiekt reprezentujący wynik zapytania paginowanego
     * @param <T>  typ danych na stronie
     * @return paginowana odpowiedź gotowa do zwrócenia z kontrolera
     */
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
