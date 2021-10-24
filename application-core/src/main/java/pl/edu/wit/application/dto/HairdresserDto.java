package pl.edu.wit.application.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class HairdresserDto {

    String id;

    String name;

    String surname;

    String photoId;

    Set<String> services;

}
