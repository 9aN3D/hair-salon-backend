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
import pl.edu.wit.hairsalon.member.MemberFacade;
import pl.edu.wit.hairsalon.member.dto.MemberContactDto;
import pl.edu.wit.hairsalon.member.dto.MemberDto;
import pl.edu.wit.hairsalon.reservation.command.ReservationCalculateCommand;
import pl.edu.wit.hairsalon.scheduledEvent.ScheduledEventFacade;
import pl.edu.wit.hairsalon.service.ServiceFacade;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;
import pl.edu.wit.hairsalon.sharedKernel.dto.FullNameDto;
import pl.edu.wit.hairsalon.sharedKernel.dto.MoneyDto;
import pl.edu.wit.hairsalon.sharedKernel.exception.ValidationException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static pl.edu.wit.hairsalon.member.dto.MemberAgreementDto.MARKETING;
import static pl.edu.wit.hairsalon.member.dto.MemberAgreementDto.PERSONAL_DATA;
import static pl.edu.wit.hairsalon.member.dto.MemberAgreementDto.RESERVATION_RECEIPT;
import static pl.edu.wit.hairsalon.reservation.dto.ReservationCalculateStepDto.SELECT_HAIRDRESSER;
import static pl.edu.wit.hairsalon.reservation.dto.ReservationCalculateStepDto.SELECT_SERVICES;
import static pl.edu.wit.hairsalon.reservation.dto.ReservationCalculateStepDto.SELECT_START_TIME;
import static pl.edu.wit.hairsalon.reservation.dto.ReservationCalculateStepDto.SUMMARY;
import static pl.edu.wit.hairsalon.sharedKernel.dto.CurrencyDto.PLN;

@ExtendWith(MockitoExtension.class)
class ReservationFacadeTest {

    private final String MEMBER_ID = "1";
    private final String HAIRDRESSER_ID = "1H";
    private final String SERVICE_ID_1ST = "1S";
    private final String SERVICE_ID_2ST = "2S";
    private final BigDecimal SERVICE_PRICE_1ST = new BigDecimal("50");
    private final BigDecimal SERVICE_PRICE_2ST = new BigDecimal("150");
    private final Long SERVICE_DURATION_1ST = 30L;
    private final Long SERVICE_DURATION_2ST = 60L;
    private final String SERVICE_ID_2ND = "2S";
    private final LocalDate START_DATE = LocalDate.now();
    private final LocalTime TIME_NOW = LocalTime.now();
    private ReservationFacade reservationFacade;
    @Mock
    private ScheduledEventFacade scheduledEventFacade;
    @Mock
    private HairdresserFacade hairdresserFacade;
    @Mock
    private ServiceFacade serviceFacade;

    @BeforeEach
    void init(@Mock MemberFacade memberFacade) {
        ReservationCalculator reservationCalculator = new ReservationCalculator(memberFacade, hairdresserFacade, serviceFacade, scheduledEventFacade);
        reservationFacade = new LoggableReservationFacade(new AppReservationFacade(null, null, reservationCalculator));
        Mockito.lenient().when(memberFacade.findOne(any())).thenReturn(buildMemberDto());
        Mockito.lenient().when(hairdresserFacade.findAll(any())).thenReturn(List.of(buildHairdresserDto()));
        Mockito.lenient().when(hairdresserFacade.findOne(HAIRDRESSER_ID)).thenReturn(buildHairdresserDto());
        Mockito.lenient().when(serviceFacade.findAll(any(), any())).thenReturn(buildPageServices());
        Mockito.lenient().when(scheduledEventFacade.count(any())).thenReturn(Long.valueOf(0));
        Mockito.lenient().when(hairdresserFacade.getAvailableTimeDurations(HAIRDRESSER_ID, START_DATE)).thenReturn(List.of(
                Duration.between(TIME_NOW.plusMinutes(15), TIME_NOW.plusMinutes(45))
        ));
        Mockito.lenient().when(hairdresserFacade.getAvailableStartTimes(HAIRDRESSER_ID, START_DATE, Duration.ofMinutes(SERVICE_DURATION_1ST))).thenReturn(List.of(
                TIME_NOW.plusMinutes(15), TIME_NOW.plusMinutes(30), TIME_NOW.plusMinutes(45)
        ));
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
    void shouldThrowNullPointerExceptionWhenCommandStepIsNull() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .build();
        var exception = assertThrows(NullPointerException.class,
                () -> reservationFacade.calculate(MEMBER_ID, calculateCommand));
        assertEquals("ReservationCalculator, command step must not be null", exception.getMessage());
    }

    @Test
    void shouldThrowReservationCalculationExceptionWhenCommandDateIsNull() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .step(SELECT_SERVICES)
                .build();
        var exception = assertThrows(NullPointerException.class,
                () -> reservationFacade.calculate(MEMBER_ID, calculateCommand));
        assertEquals("BaseReservationCalculation, date must not be null", exception.getMessage());
    }

    @Test
    void shouldCalculateReservationWhenCommandWithSelectedServiceStepAndWithoutSelectedServices() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .step(SELECT_SERVICES)
                .date(START_DATE)
                .build();
        //when
        var result = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(MEMBER_ID, result.memberId());
        assertEquals(calculateCommand.date(), result.date());
        assertNotNull(result.calculationTime());
        assertFalse(result.availableServices().isEmpty());
        assertTrue(result.selectedServices().isEmpty());
        assertTrue(result.availableHairdressers().isEmpty());
        assertTrue(result.selectedHairdresser().isEmpty());
        assertTrue(result.availableStartTimes().isEmpty());
        assertTrue(result.times().isEmpty());
        assertEquals(0, result.totalPrice().compareTo(BigDecimal.ZERO));
    }

    @Test
    void shouldCalculateReservationWhenCommandWithSelectedServiceStepAndOneSelectedService() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .step(SELECT_SERVICES)
                .date(START_DATE)
                .selectedServiceIds(Set.of(SERVICE_ID_1ST))
                .build();
        //when
        var result = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(MEMBER_ID, result.memberId());
        assertEquals(calculateCommand.date(), result.date());
        assertNotNull(result.calculationTime());
        assertFalse(result.availableServices().isEmpty());
        assertFalse(result.selectedServices().isEmpty());
        assertEquals(1, result.selectedServices().size());
        assertTrue(result.availableHairdressers().isEmpty());
        assertTrue(result.selectedHairdresser().isEmpty());
        assertTrue(result.availableStartTimes().isEmpty());
        assertTrue(result.times().isEmpty());
        assertNotNull(result.totalPrice());
        assertEquals(0, result.totalPrice().compareTo(SERVICE_PRICE_1ST));
    }

    @Test
    void shouldCalculateReservationWhenCommandWithSelectedHairdresserStepAndWithoutSelectedHairdresser() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .step(SELECT_HAIRDRESSER)
                .date(START_DATE)
                .selectedServiceIds(Set.of(SERVICE_ID_1ST))
                .build();
        //when
        var result = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(MEMBER_ID, result.memberId());
        assertEquals(calculateCommand.date(), result.date());
        assertNotNull(result.calculationTime());
        assertFalse(result.availableServices().isEmpty());
        assertFalse(result.selectedServices().isEmpty());
        assertEquals(1, result.selectedServices().size());
        assertFalse(result.availableHairdressers().isEmpty());
        assertEquals(1, result.availableHairdressers().size());
        assertTrue(result.selectedHairdresser().isEmpty());
        assertTrue(result.availableStartTimes().isEmpty());
        assertTrue(result.times().isEmpty());
        assertNotNull(result.totalPrice());
        assertEquals(0, result.totalPrice().compareTo(SERVICE_PRICE_1ST));
    }
    
    @Test
    void shouldCalculateReservationWhenCommandWithSelectedHairdresserStepAndSelectedHairdresser() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .step(SELECT_HAIRDRESSER)
                .date(START_DATE)
                .selectedServiceIds(Set.of(SERVICE_ID_1ST))
                .hairdresserId(HAIRDRESSER_ID)
                .build();
        //when
        var result = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(MEMBER_ID, result.memberId());
        assertEquals(calculateCommand.date(), result.date());
        assertNotNull(result.calculationTime());
        assertFalse(result.availableServices().isEmpty());
        assertFalse(result.selectedServices().isEmpty());
        assertEquals(1, result.selectedServices().size());
        assertFalse(result.availableHairdressers().isEmpty());
        assertEquals(1, result.availableHairdressers().size());
        assertEquals(HAIRDRESSER_ID, result.selectedHairdresser().get().id());
        assertTrue(result.availableStartTimes().isEmpty());
        assertTrue(result.times().isEmpty());
        assertNotNull(result.totalPrice());
        assertEquals(0, result.totalPrice().compareTo(SERVICE_PRICE_1ST));
    }

    @Test
    void shouldCalculateReservationWhenCommandWithSelectedStartTimeStepAndWithoutSelectedStartTime() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .step(SELECT_START_TIME)
                .date(START_DATE)
                .selectedServiceIds(Set.of(SERVICE_ID_1ST))
                .hairdresserId(HAIRDRESSER_ID)
                .build();
        //when
        var result = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(MEMBER_ID, result.memberId());
        assertEquals(calculateCommand.date(), result.date());
        assertNotNull(result.calculationTime());
        assertFalse(result.availableServices().isEmpty());
        assertFalse(result.selectedServices().isEmpty());
        assertEquals(1, result.selectedServices().size());
        assertFalse(result.availableHairdressers().isEmpty());
        assertEquals(1, result.availableHairdressers().size());
        assertEquals(HAIRDRESSER_ID, result.selectedHairdresser().get().id());
        assertFalse(result.availableStartTimes().isEmpty());
        assertEquals(3, result.availableStartTimes().size());
        assertTrue(result.times().isEmpty());
        assertNotNull(result.totalPrice());
        assertEquals(0, result.totalPrice().compareTo(SERVICE_PRICE_1ST));
    }
    
    @Test
    void shouldCalculateReservationWhenCommandWithSelectedStartTimeStepAndSelectedStartTime() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .step(SELECT_START_TIME)
                .date(START_DATE)
                .selectedServiceIds(Set.of(SERVICE_ID_1ST))
                .hairdresserId(HAIRDRESSER_ID)
                .startTime(TIME_NOW.plusMinutes(15))
                .build();
        //when
        var result = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(MEMBER_ID, result.memberId());
        assertEquals(calculateCommand.date(), result.date());
        assertNotNull(result.calculationTime());
        assertFalse(result.availableServices().isEmpty());
        assertFalse(result.selectedServices().isEmpty());
        assertEquals(1, result.selectedServices().size());
        assertFalse(result.availableHairdressers().isEmpty());
        assertEquals(1, result.availableHairdressers().size());
        assertEquals(HAIRDRESSER_ID, result.selectedHairdresser().get().id());
        assertFalse(result.availableStartTimes().isEmpty());
        assertEquals(3, result.availableStartTimes().size());
        assertTrue(result.times().isPresent());
        var times = result.times().get();
        assertEquals(SERVICE_DURATION_1ST, MINUTES.between(times.start(), times.end()));
        assertNotNull(result.totalPrice());
        assertEquals(0, result.totalPrice().compareTo(SERVICE_PRICE_1ST));
    }

    @Test
    void shouldCalculateReservationWhenCommandWithSummaryStep() {
        var calculateCommand = ReservationCalculateCommand.builder()
                .step(SUMMARY)
                .date(START_DATE)
                .selectedServiceIds(Set.of(SERVICE_ID_1ST))
                .hairdresserId(HAIRDRESSER_ID)
                .startTime(TIME_NOW.plusMinutes(15))
                .build();
        //when
        var result = reservationFacade.calculate(MEMBER_ID, calculateCommand);
        //then
        assertEquals(MEMBER_ID, result.memberId());
        assertEquals(calculateCommand.date(), result.date());
        assertNotNull(result.calculationTime());
        assertFalse(result.availableServices().isEmpty());
        assertFalse(result.selectedServices().isEmpty());
        assertEquals(1, result.selectedServices().size());
        assertFalse(result.availableHairdressers().isEmpty());
        assertEquals(1, result.availableHairdressers().size());
        assertEquals(HAIRDRESSER_ID, result.selectedHairdresser().get().id());
        assertFalse(result.availableStartTimes().isEmpty());
        assertEquals(3, result.availableStartTimes().size());
        assertTrue(result.times().isPresent());
        var times = result.times().get();
        assertEquals(SERVICE_DURATION_1ST, MINUTES.between(times.start(), times.end()));
        assertNotNull(result.totalPrice());
        assertEquals(0, result.totalPrice().compareTo(SERVICE_PRICE_1ST));
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

    private Page<ServiceDto> buildPageServices() {
        return new PageImpl<>(List.of(
                new ServiceDto(SERVICE_ID_1ST, "service 1", new MoneyDto(SERVICE_PRICE_1ST, PLN), SERVICE_DURATION_1ST),
                new ServiceDto(SERVICE_ID_2ST, "service 2", new MoneyDto(SERVICE_PRICE_2ST, PLN), SERVICE_DURATION_2ST)
        ));
    }

}
