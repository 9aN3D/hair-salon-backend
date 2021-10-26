package pl.edu.wit.member.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.edu.wit.member.port.primary.MemberRegistrationService;
import pl.edu.wit.member.port.primary.MemberService;
import pl.edu.wit.member.service.AppMemberRegistrationService;
import pl.edu.wit.member.service.AppMemberService;

@Configuration
@EnableMongoRepositories(basePackages = "pl.edu.wit.member.adapter")
public class MemberConfiguration {

    @ConditionalOnMissingBean(MemberRegistrationService.class)
    @Import(AppMemberRegistrationService.class)
    @Configuration
    static class MemberRegistrationServiceConfiguration {

    }

    @ConditionalOnMissingBean(MemberService.class)
    @Import(AppMemberService.class)
    @Configuration
    static class MemberServiceConfiguration {

    }

}
