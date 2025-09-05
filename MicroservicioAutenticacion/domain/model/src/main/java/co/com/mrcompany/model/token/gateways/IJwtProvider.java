package co.com.mrcompany.model.token.gateways;

import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.user.User;

public interface IJwtProvider {

    Token createToken(User user);

    boolean validate(String token);
}
