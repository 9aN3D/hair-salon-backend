package pl.edu.wit.product.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.wit.product.dto.ProductCategoryDto;
import pl.edu.wit.product.dto.ProductDto;
import pl.edu.wit.product.adapter.model.ProductCategoryDocument;
import pl.edu.wit.product.adapter.model.ProductDocument;

@Component
@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    public void setProductCategoryMapper(ProductCategoryMapper productCategoryMapper) {
        this.productCategoryMapper = productCategoryMapper;
    }

    @Mapping(source = "productDocument", target = "category", qualifiedByName = "toProductCategoryDto")
    public abstract ProductDto toDto(ProductDocument productDocument);

    @Mapping(source = "productDto", target = "category", qualifiedByName = "toProductCategoryDocument")
    public abstract ProductDocument toDocument(ProductDto productDto);

    @Named("toProductCategoryDto")
    ProductCategoryDto toProductCategoryDto(ProductDocument productDocument) {
        return productCategoryMapper.toDto(productDocument.getCategory());
    }

    @Named("toProductCategoryDocument")
    ProductCategoryDocument toProductCategoryDocument(ProductDto productDto) {
        return productCategoryMapper.toDocument(productDto.getCategory());
    }

}
