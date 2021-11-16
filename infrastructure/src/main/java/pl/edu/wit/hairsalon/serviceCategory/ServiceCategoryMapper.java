package pl.edu.wit.hairsalon.serviceCategory;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;

@Component
@Mapper(componentModel = "spring")
abstract class ServiceCategoryMapper {

    @Mapping(source = "itemIds", target = "serviceIds")
    abstract ServiceCategoryDocument toDocument(ServiceCategoryDto productCategory);

    @Mapping(source = "serviceIds", target = "itemIds")
    abstract ServiceCategoryDto toDto(ServiceCategoryDocument serviceCategoryDocument);

}
