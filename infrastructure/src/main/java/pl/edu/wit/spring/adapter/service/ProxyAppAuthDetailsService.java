package pl.edu.wit.spring.adapter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wit.application.command.CreateAuthDetailsCommand;
import pl.edu.wit.application.factory.AuthDetailsFactory;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.secondary.AuthDetailsRepository;
import pl.edu.wit.application.service.AppAuthDetailsService;
import pl.edu.wit.domain.dto.AuthDetailsDto;

@Service
public class ProxyAppAuthDetailsService implements AuthDetailsService {

    private final AuthDetailsService authDetailsService;

    @Autowired
    public ProxyAppAuthDetailsService(AuthDetailsRepository repository, AuthDetailsFactory authDetailsFactory) {
        this.authDetailsService = new AppAuthDetailsService(repository, authDetailsFactory);
    }

    @Override
    @Transactional
    public String create(CreateAuthDetailsCommand command) {
        return authDetailsService.create(command);
    }

    @Override
    public AuthDetailsDto findOneByEmail(String email) {
        return authDetailsService.findOneByEmail(email);
    }

}
