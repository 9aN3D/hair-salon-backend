package pl.edu.wit.hairsalon.web.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.wit.hairsalon.appointment.AppointmentFacade;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.setting.SettingFacade;
import pl.edu.wit.hairsalon.setting.dto.SettingDto;
import pl.edu.wit.hairsalon.setting.query.SettingGroupFindQuery;
import pl.edu.wit.hairsalon.socialintegration.SocialIntegrationFacade;
import pl.edu.wit.hairsalon.socialintegration.command.LinkAddingCalendarEventGenerateCommand;
import pl.edu.wit.hairsalon.uploadablefile.UploadableFileFacade;
import pl.edu.wit.hairsalon.web.response.AppointmentConciseResponse;
import pl.edu.wit.hairsalon.web.response.AppointmentResponse;
import pl.edu.wit.hairsalon.web.response.HairdresserResponse;
import pl.edu.wit.hairsalon.web.response.LinkAddingGoogleCalendarEventResponse;

import java.util.stream.Collectors;

import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_CITY;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_POSTAL_CODE;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_STREET_NAME;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_ADDRESS_STREET_NUMBER;
import static pl.edu.wit.hairsalon.setting.dto.SettingIdDto.SALON_NAME;
import static pl.edu.wit.hairsalon.socialintegration.dto.SocialProviderDto.GOOGLE;

@Service
@RequiredArgsConstructor
public class AppointmentResponseAdapter {

    private final AppointmentFacade appointmentFacade;
    private final HairdresserFacade hairdresserFacade;
    private final UploadableFileFacade uploadableFileFacade;
    private final SocialIntegrationFacade socialIntegrationFacade;
    private final SettingFacade settingFacade;

    public Page<AppointmentConciseResponse> findAll(String memberId, AppointmentFindQuery findQuery, Pageable pageable) {
        return appointmentFacade.findAll(findQuery.withMemberId(memberId), pageable)
                .map(AppointmentConciseResponse::of);
    }

    public AppointmentResponse findOne(String memberId, String appointmentId) {
        var appointment = appointmentFacade.findOne(AppointmentFindQuery.with(memberId, appointmentId));
        var hairdresserResponse = HairdresserResponse.of(hairdresserFacade.findOne(appointment.getHairdresserId()), uploadableFileFacade::findOne);
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .times(appointment.getTimes())
                .appointmentServices(appointment.getServices())
                .status(appointment.getStatus())
                .hairdresser(hairdresserResponse)
                .build();
    }

    public void resign(String memberId, String appointmentId) {
        appointmentFacade.resign(memberId, appointmentId);
    }

    public LinkAddingGoogleCalendarEventResponse getLinkAddingGoogleCalendarEvent(String memberId, String appointmentId) {
        var appointment = appointmentFacade.findOne(AppointmentFindQuery.with(memberId, appointmentId));
        return new LinkAddingGoogleCalendarEventResponse(socialIntegrationFacade.generateLinkAddingCalendarEvent(
                LinkAddingCalendarEventGenerateCommand.builder()
                        .socialProvider(GOOGLE)
                        .times(appointment.getTimes())
                        .eventName(appointment.getServices().getName())
                        .location(prepareLocation())
                        .build()
        ));
    }

    private String prepareLocation() {
        var salonName = settingFacade.findOne(SALON_NAME).getValue();
        var settingIdToValueMap = settingFacade.findAll(SettingGroupFindQuery.ofSalonAddressGroup()).stream()
                .collect(Collectors.toMap(SettingDto::getId, SettingDto::getValue));
        return new StringBuilder()
                .append(settingIdToValueMap.getOrDefault(SALON_ADDRESS_CITY, ""))
                .append(" ")
                .append(salonName)
                .append(", ")
                .append(settingIdToValueMap.getOrDefault(SALON_ADDRESS_STREET_NAME, ""))
                .append(" ")
                .append(settingIdToValueMap.getOrDefault(SALON_ADDRESS_STREET_NUMBER, ""))
                .append(", ")
                .append(settingIdToValueMap.getOrDefault(SALON_ADDRESS_POSTAL_CODE, ""))
                .toString();
    }

}
