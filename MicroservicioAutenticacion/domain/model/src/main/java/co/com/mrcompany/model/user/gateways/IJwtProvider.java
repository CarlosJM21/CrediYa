package co.com.mrcompany.model.user.gateways;

import co.com.mrcompany.model.user.Token;
import co.com.mrcompany.model.user.User;

public interface IJwtProvider {

    Token createToken(User user);

    boolean validate(String token);
}
