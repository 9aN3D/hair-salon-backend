package pl.edu.wit.hairsalon.appointment;

import lombok.RequiredArgsConstructor;
import pl.edu.wit.hairsalon.appointment.dto.AppointmentDto;
import pl.edu.wit.hairsalon.appointment.query.AppointmentFindQuery;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.notification.NotificationFacade;
import pl.edu.wit.hairsalon.notification.command.NotificationSendCommand;
import pl.edu.wit.hairsalon.notification.dto.EmailNotificationContentDto;
import pl.edu.wit.hairsalon.notification.dto.SmsNotificationContentDto;
import pl.edu.wit.hairsalon.setting.SettingFacade;
import pl.edu.wit.hairsalon.setting.dto.SettingIdDto;

import static pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto.EMAIL;
import static pl.edu.wit.hairsalon.notification.dto.NotificationTypeDto.SMS;

@RequiredArgsConstructor
class AppointmentNotificationSender {

    private final AppointmentPort appointmentPort;
    private final MemberFacade memberFacade;
    private final NotificationFacade notificationFacade;
    private final SettingFacade settingFacade;

    void send(String appointmentId) {
        var appointmentDto = appointmentPort.findOneOrThrow(AppointmentFindQuery.with(appointmentId));
        var member = memberFacade.findOne(appointmentDto.getMemberId());
        var salonName = settingFacade.findOne(SettingIdDto.SALON_NAME).getValue();
        var appointment = new Appointment(appointmentDto).validate();
        notificationFacade.send(buildSmsNotificationSendCommand(appointmentDto, member, salonName));
        notificationFacade.send(buildEmailNotificationSendCommand(appointmentDto, member, salonName));
        appointmentPort.save(appointment.changeNotificationSent().toDto());
    }

    private NotificationSendCommand buildEmailNotificationSendCommand(AppointmentDto appointment, MemberDto member, String salonName) {
        return NotificationSendCommand.builder()
                .recipientId(member.getId())
                .type(EMAIL)
                .content(EmailNotificationContentDto.builder()
                        .to(member.getContact().getEmail())
                        .subject("Przypomienia o wizycie")
                        .body(prepareNotificationContentBody(appointment, salonName))
                        .build())
                .build();
    }

    private NotificationSendCommand buildSmsNotificationSendCommand(AppointmentDto appointment, MemberDto member, String salonName) {
        return NotificationSendCommand.builder()
                .recipientId(member.getId())
                .type(SMS)
                .content(SmsNotificationContentDto.builder()
                        .to(member.getContact().getPhone())
                        .body(prepareNotificationContentBody(appointment, salonName))
                        .build())
                .build();
    }

    private String prepareNotificationContentBody(AppointmentDto appointment, String salonName) {
        var startDateTime = appointment.getTimes().getStart();
        return "Przypominam o wizycie w " + salonName + " " + startDateTime.toLocalDate() + " o " + startDateTime.toLocalTime();
    }

}
