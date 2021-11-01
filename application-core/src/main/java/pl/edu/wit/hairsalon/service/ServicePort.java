package pl.edu.wit.hairsalon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;

import java.util.Optional;

public interface ServicePort {

    String save(ServiceDto product);

    Optional<ServiceDto> findOne(ServiceFindQuery findQuery);

    ServiceDto findOneOrThrow(ServiceFindQuery findQuery);

    Page<ServiceDto> findAll(ServiceFindQuery findQuery, Pageable pageable);

    long count(ServiceFindQuery findQuery);

}
