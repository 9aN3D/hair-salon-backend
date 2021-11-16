package pl.edu.wit.hairsalon.serviceCategory.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Set;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class ServiceCategoryDto {

    String id;

    String name;

    Integer order;

    ServiceCategoryStatusDto status;

    Set<String> itemIds;

}
