package pl.edu.wit.hairsalon.serviceCategory.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record ServiceCategoryDto(String id,
                                 String name,
                                 Integer order,
                                 ServiceCategoryStatusDto status,
                                 Set<String> itemIds) {

}
