package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wit.hairsalon.hairdresser.HairdresserDayOverrideFacade;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideCreateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserDayOverrideUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.command.HairdresserUpdateCommand;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDayOverrideIdDto;
import pl.edu.wit.hairsalon.hairdresser.query.HairdresserFindQuery;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;
import pl.edu.wit.hairsalon.uploadableFile.command.FileUploadCommand;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;
import pl.edu.wit.hairsalon.web.response.PagedResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.lang.String.format;

/**
 * Adapter odpowiedzialny za operacje na fryzjerach i ich dostępności,
 * przystosowujący fasady domenowe do użycia w kontrolerach REST.
 * <p>
 * Oferuje operacje tworzenia, aktualizacji, pobierania oraz zarządzania zdjęciami
 * i wyjątkami w grafiku pracy fryzjerów.
 * </p>
 *
 * <p>Łączy dane z trzech fasad:</p>
 * <ul>
 *     <li>{@link HairdresserFacade} – logika główna dotycząca fryzjerów,</li>
 *     <li>{@link UploadableFileFacade} – obsługa zdjęć profilowych,</li>
 *     <li>{@link HairdresserDayOverrideFacade} – wyjątki od standardowych godzin pracy.</li>
 * </ul>
 *
 * @see HairdresserResponse
 * @see HairdresserCreateCommand
 * @see HairdresserDayOverrideCreateCommand
 * @see FileUploadCommand
 */
@Service
public class HairdresserResponseAdapter {

    private final HairdresserFacade hairdresserFacade;
    private final UploadableFileFacade uploadableFileFacade;
    private final HairdresserDayOverrideFacade hairdresserDayOverrideFacade;

    public HairdresserResponseAdapter(HairdresserFacade hairdresserFacade,
                                      UploadableFileFacade uploadableFileFacade,
                                      HairdresserDayOverrideFacade hairdresserDayOverrideFacade) {
        this.hairdresserFacade = hairdresserFacade;
        this.uploadableFileFacade = uploadableFileFacade;
        this.hairdresserDayOverrideFacade = hairdresserDayOverrideFacade;
    }

    /**
     * Tworzy nowego fryzjera na podstawie komendy.
     *
     * @param command dane wejściowe fryzjera
     * @return identyfikator nowo utworzonego fryzjera
     */
    public String create(HairdresserCreateCommand command) {
        return hairdresserFacade.create(command);
    }

    /**
     * Przesyła zdjęcie profilowe fryzjera.
     *
     * @param hairdresserId identyfikator fryzjera
     * @param file           zdjęcie jako multipart
     */
    public void uploadPhoto(String hairdresserId, MultipartFile file) {
        hairdresserFacade.uploadPhoto(hairdresserId, buildFileUploadCommand(file));
    }

    /**
     * Aktualizuje dane fryzjera.
     *
     * @param hairdresserId identyfikator fryzjera
     * @param command        dane do aktualizacji
     */
    public void update(String hairdresserId, HairdresserUpdateCommand command) {
        hairdresserFacade.update(hairdresserId, command);
    }

    /**
     * Pobiera szczegóły fryzjera, w tym zdjęcie profilowe (jeśli istnieje).
     *
     * @param hairdresserId identyfikator fryzjera
     * @return odpowiedź z danymi fryzjera
     */
    public HairdresserResponse findOne(String hairdresserId) {
        return HairdresserResponse.of(hairdresserFacade.findOne(hairdresserId), uploadableFileFacade::findOne);
    }

    /**
     * Zwraca paginowaną listę fryzjerów spełniających podane kryteria.
     *
     * @param findQuery zapytanie filtrujące
     * @param pageable  dane paginacji
     * @return stronicowana odpowiedź z fryzjerami
     */
    public PagedResponse<HairdresserResponse> findAll(HairdresserFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(hairdresserFacade.findAll(findQuery, pageable)
                .map(hairdresserDto -> HairdresserResponse.of(hairdresserDto, uploadableFileFacade::findOne))
        );
    }

    /**
     * Zwraca dostępne godziny rozpoczęcia wizyt danego fryzjera w określonym dniu.
     *
     * @param hairdresserId identyfikator fryzjera
     * @param date          data
     * @return lista godzin rozpoczęcia (np. 09:00:00, 09:30:00, ...)
     */
    public List<LocalTime> getAvailableStartTimes(String hairdresserId, LocalDate date) {
        return hairdresserFacade.getAvailableStartTimes(hairdresserId, date);
    }

    /**
     * Tworzy wyjątek w harmonogramie fryzjera (np. zamknięcie).
     *
     * @param command dane wyjątku
     * @return identyfikator utworzonego wyjątku
     */
    public HairdresserDayOverrideIdDto createDayOverride(HairdresserDayOverrideCreateCommand command) {
        return hairdresserDayOverrideFacade.create(command);
    }

    /**
     * Aktualizuje istniejący wyjątek w harmonogramie fryzjera.
     *
     * @param id      identyfikator wyjątku
     * @param command dane aktualizacyjne
     */
    public void updateDayOverride(HairdresserDayOverrideIdDto id, HairdresserDayOverrideUpdateCommand command) {
        hairdresserDayOverrideFacade.update(id, command);
    }

    /**
     * Buduje komendę przesyłania pliku na podstawie danych z pliku multipart.
     *
     * @param file plik przesyłany przez klienta
     * @return komenda gotowa do użycia przez {@link UploadableFileFacade}
     */
    private FileUploadCommand buildFileUploadCommand(MultipartFile file) {
        try (var content = file.getInputStream()) {
            return FileUploadCommand.builder()
                    .originalFilename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .content(content)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(
                    format("Cant build file uploadCommand, message: %s", e.getMessage()), e
            );
        }
    }

}
