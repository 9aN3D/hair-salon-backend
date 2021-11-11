package pl.edu.wit.hairsalon.servicecategory.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryStatusDto;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ServiceCategoryBaseCommand {

    private String name;

    private Integer order;

    private ServiceCategoryStatusDto status;

    private Set<String> serviceIds;

}
