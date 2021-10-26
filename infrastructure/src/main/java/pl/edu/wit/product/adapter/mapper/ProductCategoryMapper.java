package pl.edu.wit.product.adapter.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.edu.wit.product.dto.ProductCategoryDto;
import pl.edu.wit.product.adapter.model.ProductCategoryDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class ProductCategoryMapper {

    public abstract ProductCategoryDocument toDocument(ProductCategoryDto productCategory);

    public abstract ProductCategoryDto toDto(ProductCategoryDocument productCategoryDocument);

}
