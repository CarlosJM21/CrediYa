package co.com.mrcompany.security.jwt;

import co.com.mrcompany.model.token.gateways.IJwtProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    public Stream<String> getRole(String token)
    {
        return Stream.of(getClaims(token).get("roles"))
                .map(role -> (List<Map<String, String>>) role)
                .flatMap(Collection::stream)
                .map(r -> r.get("authority"));
    }

    public Stream<String> getRoleClaim(Claims claims)
    {
        return Stream.of(claims.get("roles"))
                .map(role -> (List<Map<String, String>>) role)
                .flatMap(Collection::stream)
                .map(r -> r.get("authority"));
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
