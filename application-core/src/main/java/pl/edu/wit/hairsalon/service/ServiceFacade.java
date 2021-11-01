package pl.edu.wit.hairsalon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wit.hairsalon.service.command.ServiceCreateCommand;
import pl.edu.wit.hairsalon.service.command.ServiceUpdateCommand;
import pl.edu.wit.hairsalon.service.dto.ServiceDto;
import pl.edu.wit.hairsalon.service.query.ServiceFindQuery;

public interface ServiceFacade {

    String create(ServiceCreateCommand command);

    void update(String serviceId, ServiceUpdateCommand command);

    ServiceDto findOne(String serviceId);

    Page<ServiceDto> findAll(ServiceFindQuery findQuery, Pageable pageable);

    long count(ServiceFindQuery findQuery);

}
