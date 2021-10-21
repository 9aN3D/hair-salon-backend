package pl.edu.wit.spring.adapter.persistence.product.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.application.dto.ProductCategoryDto;
import pl.edu.wit.spring.adapter.persistence.product.model.ProductCategoryDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class ProductCategoryMapper {

    public abstract ProductCategoryDocument toDocument(ProductCategoryDto productCategory);

    public abstract ProductCategoryDto toDto(ProductCategoryDocument productCategoryDocument);

}
