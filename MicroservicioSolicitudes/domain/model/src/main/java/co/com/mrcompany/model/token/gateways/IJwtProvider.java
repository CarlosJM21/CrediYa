package co.com.mrcompany.model.token.gateways;

public interface IJwtProvider {
    boolean validate(String token);
}
