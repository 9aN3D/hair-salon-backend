package pl.edu.wit.hairsalon.service;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;

@Component
@Mapper(builder = @Builder(disableBuilder = true),componentModel = "spring")
abstract class ServiceMapper {

    abstract ServiceDto toDto(ServiceDocument serviceDocument);

    abstract ServiceDocument toDocument(ServiceDto serviceDto);

}
