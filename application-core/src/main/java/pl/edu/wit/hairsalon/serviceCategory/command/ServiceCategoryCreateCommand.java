package pl.edu.wit.hairsalon.serviceCategory.command;

import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryStatusDto;

import java.util.Set;

public record ServiceCategoryCreateCommand(
        String name,
        Integer order,
        ServiceCategoryStatusDto status,
        Set<String> serviceIds
) {

}
