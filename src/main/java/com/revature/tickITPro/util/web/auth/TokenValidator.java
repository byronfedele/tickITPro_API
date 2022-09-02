package com.revature.tickITPro.util.web.auth;

import com.revature.tickITPro.util.exceptions.UnauthorizedException;
import com.revature.tickITPro.util.web.auth.dto.response.Principal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TokenValidator {

    private final JwtConfig jwtConfig;

    public TokenValidator(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public Optional<Principal> parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
            return Optional.of(new Principal(claims.getId(), claims.getSubject(), claims.get("isAdmin",Boolean.class), claims.get("isITPro",Boolean.class)));

        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException(e.getMessage());
        }
    }

    public int getDefaultTokenExpiry() {
        return jwtConfig.getExpiration();
    }
}
