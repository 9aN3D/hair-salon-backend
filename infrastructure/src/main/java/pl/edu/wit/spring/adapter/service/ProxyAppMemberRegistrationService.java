package pl.edu.wit.spring.adapter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wit.application.command.RegisterMemberCommand;
import pl.edu.wit.application.factory.MemberFactory;
import pl.edu.wit.application.port.primary.AuthDetailsService;
import pl.edu.wit.application.port.primary.MemberRegistrationService;
import pl.edu.wit.application.port.secondary.MemberRepository;
import pl.edu.wit.application.service.AppMemberRegistrationService;

@Service
public class ProxyAppMemberRegistrationService implements MemberRegistrationService {

    private final MemberRegistrationService memberRegistrationService;

    @Autowired
    public ProxyAppMemberRegistrationService(MemberRepository repository, AuthDetailsService authDetailsService, MemberFactory factory) {
        this.memberRegistrationService = new AppMemberRegistrationService(repository, authDetailsService, factory);
    }

    @Override
    @Transactional
    public void register(RegisterMemberCommand command) {
        memberRegistrationService.register(command);
    }

}
