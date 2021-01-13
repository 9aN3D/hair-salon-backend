package pl.edu.wit.spring.adapter.service;

import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.secondary.AuthDetailsDao;
import pl.edu.wit.application.service.AppAuthDetailsService;

@Service
public class ProxyAppAuthDetailsService implements AuthDetailsService {

    @Delegate
    private final AuthDetailsService authDetailsService;

    @Autowired
    public ProxyAppAuthDetailsService(AuthDetailsDao dao) {
        this.authDetailsService = new AppAuthDetailsService(dao);
    }

}
