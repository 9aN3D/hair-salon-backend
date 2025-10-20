package pl.edu.wit.hairsalon.appointment;

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

class AppointmentNotificationSender {

    private final AppointmentPort appointmentPort;
    private final MemberFacade memberFacade;
    private final NotificationFacade notificationFacade;
    private final SettingFacade settingFacade;

    AppointmentNotificationSender(AppointmentPort appointmentPort, MemberFacade memberFacade, NotificationFacade notificationFacade, SettingFacade settingFacade) {
        this.appointmentPort = appointmentPort;
        this.memberFacade = memberFacade;
        this.notificationFacade = notificationFacade;
        this.settingFacade = settingFacade;
    }

    void send(String appointmentId) {
        var appointmentDto = appointmentPort.findOneOrThrow(AppointmentFindQuery.with(appointmentId));
        var member = memberFacade.findOne(appointmentDto.memberId());
        var salonName = settingFacade.findOne(SettingIdDto.SALON_NAME).value();
        var appointment = new Appointment(appointmentDto).validate();
        notificationFacade.send(buildSmsNotificationSendCommand(appointmentDto, member, salonName));
        notificationFacade.send(buildEmailNotificationSendCommand(appointmentDto, member, salonName));
        appointmentPort.save(appointment.changeNotificationSent().toDto());
    }

    private NotificationSendCommand buildEmailNotificationSendCommand(AppointmentDto appointment, MemberDto member, String salonName) {
        return new NotificationSendCommand(
                member.id(),
                EMAIL,
                EmailNotificationContentDto.builder()
                        .to(member.contact().email())
                        .subject("Przypomienia o wizycie")
                        .body(prepareNotificationContentBody(appointment, salonName))
                        .build()
        );
    }

    private NotificationSendCommand buildSmsNotificationSendCommand(AppointmentDto appointment, MemberDto member, String salonName) {
        return new NotificationSendCommand(
                member.id(),
                SMS,
                new SmsNotificationContentDto(
                        member.contact().phone(),
                        prepareNotificationContentBody(appointment, salonName)
                )
        );
    }

    private String prepareNotificationContentBody(AppointmentDto appointment, String salonName) {
        var startDateTime = appointment.times().start();
        return "Przypominam o wizycie w " + salonName + " " + startDateTime.toLocalDate() + " o " + startDateTime.toLocalTime();
    }

}
