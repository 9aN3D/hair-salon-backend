package pl.edu.wit.hairsalon.sharedKernel.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@RequiredArgsConstructor
public class DateRangeDto {

    LocalDateTime start;
    LocalDateTime end;

}
