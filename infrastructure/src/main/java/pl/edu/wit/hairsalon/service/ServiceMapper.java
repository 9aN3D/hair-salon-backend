package pl.edu.wit.hairsalon.service;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;

@Component
@Mapper(componentModel = "spring")
abstract class ServiceMapper {

    abstract ServiceDto toDto(ServiceDocument serviceDocument);

    abstract ServiceDocument toDocument(ServiceDto serviceDto);

}
