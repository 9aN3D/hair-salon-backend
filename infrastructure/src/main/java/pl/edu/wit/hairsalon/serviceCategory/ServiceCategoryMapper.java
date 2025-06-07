package pl.edu.wit.hairsalon.serviceCategory;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;

@Component
@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
abstract class ServiceCategoryMapper {

    @Mapping(target = "serviceIds", source = "itemIds")
    abstract ServiceCategoryDocument toDocument(ServiceCategoryDto productCategory);

    @Mapping(target = "itemIds", source = "serviceIds")
    abstract ServiceCategoryDto toDto(ServiceCategoryDocument serviceCategoryDocument);

}
