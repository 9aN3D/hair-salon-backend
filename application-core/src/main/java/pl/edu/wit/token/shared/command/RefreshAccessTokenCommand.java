package pl.edu.wit.token.shared.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshAccessTokenCommand {

    private String refreshToken;

}
