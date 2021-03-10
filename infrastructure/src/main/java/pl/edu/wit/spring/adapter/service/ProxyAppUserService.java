package pl.edu.wit.spring.adapter.service;

import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wit.application.port.primary.UserService;
import pl.edu.wit.application.port.secondary.UserDao;
import pl.edu.wit.application.service.AppUserService;

@Service
public class ProxyAppUserService implements UserService {

    @Delegate
    private final UserService userService;

    @Autowired
    public ProxyAppUserService(UserDao dao) {
        this.userService = new AppUserService(dao);
    }

}
