package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.command.MemberUpdateCommand;
import pl.edu.wit.hairsalon.member.query.MemberFindQuery;
import pl.edu.wit.hairsalon.web.response.MemberResponse;
import pl.edu.wit.hairsalon.web.response.PagedResponse;

/**
 * Adapter odpowiedzialny za operacje związane z członkami systemu (klientami).
 * <p>
 * Pełni rolę warstwy pośredniczącej między fasadą domenową {@link MemberFacade}
 * a warstwą kontrolera REST, dostarczając odpowiedzi w postaci {@link MemberResponse}.
 * </p>
 *
 * <p>Główne zadania:</p>
 * <ul>
 *     <li>Wyszukiwanie członków (paginowane),</li>
 *     <li>Pobieranie danych pojedynczego członka,</li>
 *     <li>Aktualizacja danych członka.</li>
 * </ul>
 *
 * @see MemberResponse
 * @see MemberFacade
 * @see MemberFindQuery
 * @see MemberUpdateCommand
 */
@Service
public class MemberResponseAdapter {

    private final MemberFacade memberFacade;

    /**
     * Tworzy instancję adaptera z przekazaną fasadą członka.
     *
     * @param memberFacade fasada domenowa zarządzająca członkami
     */
    public MemberResponseAdapter(MemberFacade memberFacade) {
        this.memberFacade = memberFacade;
    }

    /**
     * Zwraca dane jednego członka (klienta) na podstawie jego identyfikatora.
     *
     * @param memberId identyfikator członka
     * @return odpowiedź z danymi członka
     */
    public MemberResponse findOne(String memberId) {
        return MemberResponse.of(memberFacade.findOne(memberId));
    }

    /**
     * Aktualizuje dane członka na podstawie przekazanej komendy.
     *
     * @param memberId identyfikator członka
     * @param command  dane do aktualizacji (np. imię, kontakt)
     */
    public void update(String memberId, MemberUpdateCommand command) {
        memberFacade.update(memberId, command);
    }

    /**
     * Zwraca listę członków w sposób paginowany, zgodnie z przekazanym zapytaniem i paginacją.
     *
     * @param findQuery filtr wyszukiwania
     * @param pageable  informacje o paginacji (strona, rozmiar, sortowanie)
     * @return paginowana lista członków w formacie {@link MemberResponse}
     */
    public PagedResponse<MemberResponse> findAll(MemberFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(
                memberFacade.findAll(findQuery, pageable)
                        .map(MemberResponse::of)
        );
    }

}
