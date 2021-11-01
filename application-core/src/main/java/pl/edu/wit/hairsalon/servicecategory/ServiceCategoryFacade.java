package pl.edu.wit.hairsalon.servicecategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryCreateCommand;
import pl.edu.wit.hairsalon.servicecategory.command.ServiceCategoryUpdateCommand;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery;

public interface ServiceCategoryFacade {

    void create(ServiceCategoryCreateCommand command);

    void update(String serviceCategoryId, ServiceCategoryUpdateCommand command);

    ServiceCategoryDto findOne(String serviceCategoryId);

    Page<ServiceCategoryDto> findAll(ServiceCategoryFindQuery findQuery, Pageable pageable);

}
