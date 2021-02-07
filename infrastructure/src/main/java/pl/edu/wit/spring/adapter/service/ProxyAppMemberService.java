package pl.edu.wit.spring.adapter.service;

import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wit.application.port.primary.MemberService;
import pl.edu.wit.application.port.secondary.MemberDao;
import pl.edu.wit.application.port.secondary.PasswordEncoder;
import pl.edu.wit.application.port.secondary.PhoneNumberProvider;
import pl.edu.wit.application.service.AppMemberService;

@Service
public class ProxyAppMemberService implements MemberService {

    @Delegate
    private final MemberService memberService;

    @Autowired
    public ProxyAppMemberService(MemberDao dao, PhoneNumberProvider phoneNumberProvider, PasswordEncoder passwordEncoder) {
        this.memberService = new AppMemberService(dao, phoneNumberProvider, passwordEncoder);
    }

}
