package pl.edu.wit.hairsalon.servicecategory.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryStatusDto;

import java.util.Set;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ServiceCategoryCreateCommand extends ServiceCategoryBaseCommand {

    public ServiceCategoryCreateCommand(String name, Integer order, ServiceCategoryStatusDto status, Set<String> serviceIds) {
        super(name, order, status, serviceIds);
    }

}
