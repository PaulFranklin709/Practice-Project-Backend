package revature.paulfranklin.practice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import revature.paulfranklin.practice.dtos.responses.Principal;
import revature.paulfranklin.practice.exceptions.InvalidAuthException;
import revature.paulfranklin.practice.utils.JwtConfig;

import java.util.Date;

@Service
public class TokenService {
    private final JwtConfig jwtConfig;

    public TokenService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String createNewToken(Principal subject) {
        long now = System.currentTimeMillis();

        JwtBuilder tokenBuilder = Jwts.builder()
                .setId(subject.getUserId())
                .setIssuer("Practice")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration()))
                .setSubject(subject.getUsername())
                .claim("isActive", subject.getActive())
                .claim("role", subject.getRole())
                .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        String token = tokenBuilder.compact();
        subject.setToken(token);
        return token;
    }

    public Principal retrievePrincipalFromToken(String token) throws Exception {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new InvalidAuthException("Not Authorized");
        }

        Principal principal = new Principal(
                claims.getId(),
                claims.getSubject(),
                claims.get("isActive", Boolean.class),
                claims.get("role", String.class)
        );
        principal.setToken(token);
        return principal;
    }
}
