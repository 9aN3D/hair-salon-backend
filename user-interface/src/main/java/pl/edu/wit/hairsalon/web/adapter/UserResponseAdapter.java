package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.user.UserFacade;
import pl.edu.wit.hairsalon.user.query.UserFindQuery;
import pl.edu.wit.hairsalon.web.response.PagedResponse;
import pl.edu.wit.hairsalon.web.response.UserResponse;

/**
 * Adapter odpowiedzialny za przekształcanie danych użytkowników systemowych (role: ADMIN)
 * z warstwy domenowej do formatu odpowiedzi REST.
 * <p>
 * Korzysta z {@link UserFacade}, aby pobierać dane użytkowników na podstawie zapytania
 * lub identyfikatora użytkownika.
 * </p>
 *
 * <p>Główne zadania klasy:</p>
 * <ul>
 *     <li>Odczyt pojedynczego użytkownika po identyfikatorze (np. ID sesji/autoryzacji),</li>
 *     <li>Wyszukiwanie użytkowników z użyciem paginacji i filtrów.</li>
 * </ul>
 *
 * @see UserFacade
 * @see UserResponse
 */
@Service
public class UserResponseAdapter {

    private final UserFacade userFacade;

    /**
     * Tworzy instancję adaptera użytkowników.
     *
     * @param userFacade fasada domenowa odpowiadająca za użytkowników
     */
    public UserResponseAdapter(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * Zwraca pojedynczego użytkownika na podstawie identyfikatora pochodzącego np. z kontekstu sesji.
     *
     * @param authDetailsId identyfikator użytkownika
     * @return dane użytkownika w formacie REST
     */
    public UserResponse findOne(String authDetailsId) {
        return UserResponse.of(userFacade.findOne(authDetailsId));
    }

    /**
     * Zwraca paginowaną listę użytkowników na podstawie kryteriów wyszukiwania.
     *
     * @param findQuery zapytanie filtrujące
     * @param pageable  parametry paginacji
     * @return stronicowana lista użytkowników
     */
    public PagedResponse<UserResponse> findAll(UserFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(
                userFacade.findAll(findQuery, pageable)
                        .map(UserResponse::of)
        );
    }

}
