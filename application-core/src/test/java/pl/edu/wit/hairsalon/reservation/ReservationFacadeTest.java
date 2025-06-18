package pl.edu.wit.hairsalon.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import pl.edu.wit.hairsalon.hairdresser.HairdresserFacade;
import pl.edu.wit.hairsalon.hairdresser.dto.HairdresserDto;
import pl.edu.wit.hairsalon.hairdresser.exception.HairdresserNotFoundException;
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.reservation.exception.ReservationCalculationException;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static pl.edu.wit.hairsalon.member.dto.MemberAgreementDto.MARKETING;
import static pl.edu.wit.hairsalon.member.dto.MemberAgreementDto.PERSONAL_DATA;
import static pl.edu.wit.hairsalon.member.dto.MemberAgreementDto.RESERVATION_RECEIPT;
import static pl.edu.wit.hairsalon.sharedKernel.dto.CurrencyDto.PLN;

@ExtendWith(MockitoExtension.class)
class ReservationFacadeTest {

    private final String MEMBER_ID = "1";
    private final String HAIRDRESSER_ID = "1H";
    private final String SERVICE_ID_1ST = "1S";
    private final BigDecimal SERVICE_PRICE_1ST = new BigDecimal("50");
    private final Long SERVICE_DURATION_1ST = 30L;
    private final String SERVICE_ID_2ND = "2S";
    private final BigDecimal SERVICE_PRICE_2ND = new BigDecimal("40");
    private final Long SERVICE_DURATION_2ND = 36L;
    private final String SERVICE_ID_3RD = "3S";
    private final Set<String> SERVICE_IDS = Set.of(SERVICE_ID_1ST, SERVICE_ID_2ND, SERVICE_ID_3RD);
    private final LocalDateTime START_DATE_TIME = now().plusMinutes(1);
    private ReservationFacade reservationFacade;
    @Mock
    private ScheduledEventFacade scheduledEventFacade;
    @Mock
    private HairdresserFacade hairdresserFacade;

    @BeforeEach
    void init(@Mock MemberFacade memberFacade,
              @Mock ServiceFacade serviceFacade) {
        ReservationCalculator reservationCalculator = new ReservationCalculator(memberFacade, hairdresserFacade, serviceFacade, scheduledEventFacade);
        reservationFacade = new LoggableReservationFacade(new AppReservationFacade(null, null, reservationCalculator));

        Mockito.lenient().when(memberFacade.findOne(any())).thenReturn(buildMemberDto());
        Mockito.lenient().when(hairdresserFacade.findOne(HAIRDRESSER_ID)).thenReturn(buildHairdresserDto());
        Mockito.lenient().when(serviceFacade.findAll(any(), any())).thenReturn(buildPageService());
        Mockito.lenient().when(scheduledEventFacade.count(any())).thenReturn(Long.valueOf(0));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenMemberIdIsNull() {
        var exception = assertThrows(NullPointerException.class,
                () -> reservationFacade.calculate(null, null));
        assertEquals("Member id must not be null", exception.getMessage());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenCommandIsNull() {
        var exception = assertThrows(NullPointerException.class,
                () -> reservationFacade.calculate(MEMBER_ID, null));
        assertEquals("Hairdresser service reservation calculate command must not be null", exception.getMessage());
    }

    @Test
    void shouldThrowReservationCalculationExceptionWhenCommandHairdresserIdIsNull() {
        Mockito.when(hairdresserFacade.findOne(any())).thenThrow(HairdresserNotFoundException.class);
        var calculateCommand = ReservationCalculateCommand.builder()
                .hairdresserId(null)
                .startDateTime(START_DATE_TIME)
                .build();
        assertThrows(HairdresserNotFoundException.class,
                () -> reservationFacade.calculate(MEMBER_ID, calculateCommand));
    }

    @Test
    void shouldThrowReservationCalculationExceptionWhenCommandStartDateTimeIsNull() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .hairdresserId(HAIRDRESSER_ID)
                .startDateTime(null)
                .build();
        var exception = assertThrows(NullPointerException.class,
                () -> reservationFacade.calculate(MEMBER_ID, calculateCommand));
        assertEquals("Reservation calculation start date time must not be null", exception.getMessage());
    }

    @Test
    void shouldThrowReservationCalculationExceptionWhenAppointmentCountNotEqualZero() {
        Mockito.when(scheduledEventFacade.count(any())).thenReturn(Long.valueOf(1));
        var calculateCommand = ReservationCalculateCommand.builder()
                .hairdresserId(HAIRDRESSER_ID)
                .startDateTime(START_DATE_TIME)
                .selectedServiceIds(Set.of(SERVICE_ID_1ST))
                .build();
        var exception = assertThrows(ReservationCalculationException.class,
                () -> reservationFacade.calculate(MEMBER_ID, calculateCommand));
        assertEquals(
                format(
                        "These appointment times have already reserved, {hairdresserId=1H, times=%s - %s}",
                        START_DATE_TIME, START_DATE_TIME.plusMinutes(SERVICE_DURATION_1ST)),
                exception.getMessage());
    }

    @Test
    void shouldCalculateReservationWhenCommandWithHairdresserIdAndStartDateTime() {
        //given
        var calculateCommand = ReservationCalculateCommand.builder()
                .hairdresserId(HAIRDRESSER_ID)
                .startDateTime(START_DATE_TIME)
                .build();
        //when
        var calculatedReservation = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(calculatedReservation.times().start(), calculateCommand.startDateTime());
        assertEquals(calculatedReservation.hairdresser().id(), calculateCommand.hairdresserId());
        assertEquals(calculatedReservation.hairdresser().services().size(), SERVICE_IDS.size());
        assertNull(calculatedReservation.selectedServices());
        assertNull(calculatedReservation.totalPrice());
    }

    @Test
    void shouldCalculateReservationWhenCommandWithHairdresserIdAndStartDateTimeAndOneSelectedService() {
        //given
        var calculateCommand = ReservationCalculateCommand.builder()
                .hairdresserId(HAIRDRESSER_ID)
                .startDateTime(START_DATE_TIME)
                .selectedServiceIds(Set.of(SERVICE_ID_1ST))
                .build();
        //when
        var calculatedReservation = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(calculatedReservation.times().start(), calculateCommand.startDateTime());
        assertEquals(calculatedReservation.times().end(), calculatedReservation.times().start().plusMinutes(SERVICE_DURATION_1ST));
        assertEquals(calculatedReservation.hairdresser().id(), calculateCommand.hairdresserId());
        assertEquals(calculatedReservation.hairdresser().services().size(), SERVICE_IDS.size());
        assertEquals(calculatedReservation.selectedServices().size(), calculateCommand.selectedServiceIds().size());
        assertEquals(0, calculatedReservation.totalPrice().compareTo(SERVICE_PRICE_1ST));
    }

    @Test
    void shouldCalculateReservationWhenCommandWithHairdresserIdAndStartDateTimeAndTw0SelectedService() {
        //given
        var calculateCommand = ReservationCalculateCommand.builder()
                .hairdresserId(HAIRDRESSER_ID)
                .startDateTime(START_DATE_TIME)
                .selectedServiceIds(Set.of(SERVICE_ID_1ST, SERVICE_ID_2ND))
                .build();
        //when
        var calculatedReservation = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(calculatedReservation.times().start(), calculateCommand.startDateTime());
        assertEquals(calculatedReservation.times().end(), calculatedReservation.times().start().plusMinutes(SERVICE_DURATION_1ST + SERVICE_DURATION_2ND));
        assertEquals(calculatedReservation.hairdresser().id(), calculateCommand.hairdresserId());
        assertEquals(calculatedReservation.hairdresser().services().size(), SERVICE_IDS.size());
        assertEquals(calculatedReservation.selectedServices().size(), calculateCommand.selectedServiceIds().size());
        assertEquals(0, calculatedReservation.totalPrice().compareTo(SERVICE_PRICE_1ST.add(SERVICE_PRICE_2ND)));
    }

    private MemberDto buildMemberDto() {
        return MemberDto.builder()
                .id(MEMBER_ID)
                .fullName(new FullNameDto("test", "1"))
                .contact(new MemberContactDto("email@test.com", "+48535367182"))
                .agreements(Set.of(PERSONAL_DATA,
                        RESERVATION_RECEIPT,
                        MARKETING))
                .registrationDateTime(now())
                .build();
    }

    private HairdresserDto buildHairdresserDto() {
        return HairdresserDto.builder()
                .id(HAIRDRESSER_ID)
                .fullName(new FullNameDto("test", "I"))
                .serviceIds(Set.of(SERVICE_ID_1ST, SERVICE_ID_2ND))
                .build();
    }

    private Page<ServiceDto> buildPageService() {
        return new PageImpl<>(List.of(
                new ServiceDto(SERVICE_ID_1ST, "service 1", new MoneyDto(SERVICE_PRICE_1ST, PLN), SERVICE_DURATION_1ST),
                new ServiceDto(SERVICE_ID_2ND, "service 2", new MoneyDto(SERVICE_PRICE_2ND, PLN), SERVICE_DURATION_2ND),
                new ServiceDto(SERVICE_ID_3RD, "service 3", new MoneyDto(new BigDecimal("45"), PLN), 20L)
        ));
    }

}
