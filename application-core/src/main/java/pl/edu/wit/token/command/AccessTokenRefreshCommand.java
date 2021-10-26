package pl.edu.wit.token.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenRefreshCommand {

    private String refreshToken;

}
