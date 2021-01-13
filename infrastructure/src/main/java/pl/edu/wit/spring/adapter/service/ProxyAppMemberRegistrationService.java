package pl.edu.wit.spring.adapter.service;

import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wit.application.factory.MemberFactory;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.primary.MemberRegistrationService;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.application.service.AppMemberRegistrationService;

@Service
public class ProxyAppMemberRegistrationService implements MemberRegistrationService {

    @Delegate
    private final MemberRegistrationService memberRegistrationService;

    @Autowired
    public ProxyAppMemberRegistrationService(MemberDao dao, AuthDetailsService authDetailsService, MemberFactory factory) {
        this.memberRegistrationService = new AppMemberRegistrationService(dao, factory, authDetailsService);
    }

}
