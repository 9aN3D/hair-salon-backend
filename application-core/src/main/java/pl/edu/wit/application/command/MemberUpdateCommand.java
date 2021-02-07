package pl.edu.wit.application.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberUpdateCommand extends MemberBaseCommand {

    public AuthDetailsUpdateCommand toAuthDetailsUpdateCommand() {
        return AuthDetailsUpdateCommand.builder()
                .email(getEmail())
                .password(getPassword())
                .build();
    }

}

