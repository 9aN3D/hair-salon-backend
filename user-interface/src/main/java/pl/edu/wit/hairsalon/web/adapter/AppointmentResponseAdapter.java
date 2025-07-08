package pl.edu.wit.hairsalon.web.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.setting.SettingFacade;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.query.SettingGroupFindQuery;
import pl.edu.wit.hairsalon.socialIntegration.SocialIntegrationFacade;
import pl.edu.wit.hairsalon.socialIntegration.command.LinkAddingCalendarEventGenerateCommand;
import pl.edu.wit.hairsalon.uploadableFile.UploadableFileFacade;
import pl.edu.wit.hairsalon.web.response.AppointmentConciseResponse;
import pl.edu.wit.hairsalon.web.response.AppointmentResponse;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;
import pl.edu.wit.hairsalon.web.response.LinkAddingGoogleCalendarEventResponse;
import pl.edu.wit.hairsalon.web.response.PagedResponse;

import java.util.StringJoiner;
import java.util.stream.Collectors;

import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_CITY;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_POSTAL_CODE;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_STREET_NAME;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_STREET_NUMBER;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_NAME;
import static pl.edu.wit.hairsalon.socialIntegration.dto.SocialProviderDto.GOOGLE;

/**
 * Adapter odpowiedzialny za tworzenie odpowiedzi związanych z wizytami użytkownika.
 * <p>
 * Łączy dane z różnych fasad (spotkania, fryzjerzy, pliki, ustawienia, integracje zewnętrzne)
 * w złożone modele odpowiedzi, wykorzystywane przez kontrolery REST.
 * </p>
 *
 * <p>Zakres odpowiedzialności tej klasy obejmuje:</p>
 * <ul>
 *     <li>pobieranie paginowanej listy wizyt użytkownika,</li>
 *     <li>pobieranie szczegółów jednej wizyty,</li>
 *     <li>rezygnację z wizyty,</li>
 *     <li>generowanie linku do dodania wizyty do kalendarza Google</li>
 * </ul>
 *
 * @see AppointmentFacade
 * @see HairdresserFacade
 * @see SocialIntegrationFacade
 * @see SettingFacade
 * @see UploadableFileFacade
 */
@Service
public class AppointmentResponseAdapter {

    private final AppointmentFacade appointmentFacade;
    private final HairdresserFacade hairdresserFacade;
    private final UploadableFileFacade uploadableFileFacade;
    private final SocialIntegrationFacade socialIntegrationFacade;
    private final SettingFacade settingFacade;

    public AppointmentResponseAdapter(AppointmentFacade appointmentFacade,
                                      HairdresserFacade hairdresserFacade,
                                      UploadableFileFacade uploadableFileFacade,
                                      SocialIntegrationFacade socialIntegrationFacade,
                                      SettingFacade settingFacade) {
        this.appointmentFacade = appointmentFacade;
        this.hairdresserFacade = hairdresserFacade;
        this.uploadableFileFacade = uploadableFileFacade;
        this.socialIntegrationFacade = socialIntegrationFacade;
        this.settingFacade = settingFacade;
    }

    /**
     * Zwraca listę wizyt użytkownika w formacie paginowanym.
     *
     * @param memberId  identyfikator użytkownika
     * @param findQuery kryteria wyszukiwania
     * @param pageable  parametry paginacji
     * @return lista wizyt w formacie skróconym
     */
    public PagedResponse<AppointmentConciseResponse> findAll(String memberId, AppointmentFindQuery findQuery, Pageable pageable) {
        return PagedResponse.from(
                appointmentFacade.findAll(findQuery.withMemberId(memberId), pageable)
                        .map(AppointmentConciseResponse::of)
        );
    }

    /**
     * Zwraca szczegóły pojedynczej wizyty użytkownika.
     *
     * @param memberId      identyfikator użytkownika
     * @param appointmentId identyfikator wizyty
     * @return pełna odpowiedź dotycząca wizyty
     */
    public AppointmentResponse findOne(String memberId, String appointmentId) {
        var appointment = appointmentFacade.findOne(AppointmentFindQuery.with(memberId, appointmentId));
        var hairdresserResponse = HairdresserResponse.of(hairdresserFacade.findOne(appointment.hairdresserId()), uploadableFileFacade::findOne);
        return AppointmentResponse.builder()
                .id(appointment.id())
                .times(appointment.times())
                .appointmentServices(appointment.services())
                .status(appointment.status())
                .hairdresser(hairdresserResponse)
                .build();
    }

    /**
     * Pozwala użytkownikowi zrezygnować z danej wizyty.
     *
     * @param memberId      identyfikator użytkownika
     * @param appointmentId identyfikator wizyty
     */
    public void resign(String memberId, String appointmentId) {
        appointmentFacade.resign(memberId, appointmentId);
    }

    /**
     * Generuje link do dodania wizyty do kalendarza Google.
     *
     * @param memberId      identyfikator użytkownika
     * @param appointmentId identyfikator wizyty
     * @return odpowiedź zawierająca link do Google Calendar
     */
    public LinkAddingGoogleCalendarEventResponse getLinkAddingGoogleCalendarEvent(String memberId, String appointmentId) {
        var appointment = appointmentFacade.findOne(AppointmentFindQuery.with(memberId, appointmentId));
        return new LinkAddingGoogleCalendarEventResponse(socialIntegrationFacade.generateLinkAddingCalendarEvent(
                LinkAddingCalendarEventGenerateCommand.builder()
                        .socialProvider(GOOGLE)
                        .times(appointment.times())
                        .eventName(appointment.status().name())
                        .location(prepareLocation())
                        .build()
        ));
    }

    private String prepareLocation() {
        var salonName = settingFacade.findOne(SALON_NAME).value();
        var settingIdToValueMap = settingFacade.findAll(SettingGroupFindQuery.ofSalonAddressGroup()).stream()
                .collect(Collectors.toMap(SettingDto::id, SettingDto::value));
        return new StringJoiner(", ")
                .add(new StringJoiner(" ")
                        .add(settingIdToValueMap.getOrDefault(SALON_ADDRESS_CITY, ""))
                        .add(salonName)
                        .toString())
                .add(settingIdToValueMap.getOrDefault(SALON_ADDRESS_STREET_NAME, "") + " " + settingIdToValueMap.getOrDefault(SALON_ADDRESS_STREET_NUMBER, ""))
                .add(settingIdToValueMap.getOrDefault(SALON_ADDRESS_POSTAL_CODE, ""))
                .toString();
    }

}
