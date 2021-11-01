package pl.edu.wit.hairsalon.servicecategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.servicecategory.dto.ServiceCategoryDto;
import pl.edu.wit.hairsalon.servicecategory.query.ServiceCategoryFindQuery;

import java.util.Optional;

public interface ServiceCategoryPort {

    String save(ServiceCategoryDto serviceCategory);

    Optional<ServiceCategoryDto> findOne(ServiceCategoryFindQuery query);

    ServiceCategoryDto findOneOrThrow(ServiceCategoryFindQuery query);

    Page<ServiceCategoryDto> findAll(ServiceCategoryFindQuery findQuery, Pageable pageable);

}
