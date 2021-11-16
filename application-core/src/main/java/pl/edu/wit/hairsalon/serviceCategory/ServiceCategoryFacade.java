package pl.edu.wit.hairsalon.serviceCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.serviceCategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.serviceCategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.serviceCategory.query.ServiceCategoryFindQuery;

public interface ServiceCategoryFacade {

    void create(ServiceCategoryCreateCommand command);

    void update(String serviceCategoryId, ServiceCategoryUpdateCommand command);

    ServiceCategoryDto findOne(String serviceCategoryId);

    Page<ServiceCategoryDto> findAll(ServiceCategoryFindQuery findQuery, Pageable pageable);

}
