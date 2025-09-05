package co.com.mrcompany.security.jwt;


import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.token.gateways.IJwtProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Component
public class JwtProvider implements IJwtProvider {

    private static final Logger LOGGER =  Logger.getLogger(JwtProvider.class.getName());

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expiration;
    @Value("${jwt.expirationRefresh}")
    private Integer expirationRefresh;

    public Token createToken(User user){
        var token =  new Token();
        token.setEmail(user.getEmail());
        token.setToken(generateToken(user, expiration,"basic"));
        token.setTokenRefresh(generateToken(user, (expiration+ expirationRefresh), "refresh"));
        token.setIsValid(true);
        return token;
    }

    public String generateToken(User user, Integer time, String type) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("type", type )
                .claim("roles", generateAuthority(user.getIdRol().toString()) )
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + time))
                .signWith(getKey(secret))
                .compact();
    }

    private Collection<? extends GrantedAuthority> generateAuthority(String role){
        String[] roles = new String[]{role};
        return Stream.of(roles).map(SimpleGrantedAuthority::new)
                .toList();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validate(String token){
        try {
            Jwts.parser()
                    .verifyWith(getKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.severe( "Token Expires");
        } catch (UnsupportedJwtException e) {
            LOGGER.severe("token unsupported");
        } catch (MalformedJwtException e) {
            LOGGER.severe("token malformed");
        } catch (SignatureException e) {
            LOGGER.severe("bad signature");
        } catch (IllegalArgumentException e) {
            LOGGER.severe("illegal args");
        }
        return false;
    }

    private SecretKey getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
