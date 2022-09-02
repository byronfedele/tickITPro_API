package com.revature.tickITPro.util.web.auth;

import com.revature.tickITPro.util.exceptions.InvalidTokenException;
import com.revature.tickITPro.util.exceptions.InvalidUserInputException;
import com.revature.tickITPro.util.exceptions.UnauthorizedException;
import com.revature.tickITPro.util.web.auth.dto.response.Principal;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenValidator tokenValidator;
    private final TokenGenerator tokenGenerator;

    public TokenService(TokenGenerator tokenGenerator, TokenValidator tokenValidator) {
        this.tokenGenerator = tokenGenerator;
        this.tokenValidator = tokenValidator;
    }

    public String generateToken(Principal subject) {
        if (!isPrincipalValid(subject)) throw new InvalidUserInputException("Provided with invalid principal object");
        return tokenGenerator.createToken(subject);
    }

    public boolean isTokenValid(String token) {
        if (token.equals(null) || token.trim().equals("")) return false;
        return tokenValidator.parseToken(token).isPresent();
    }

    public Principal extractTokenDetails(String token) {
        if (token.equals(null) || token.trim().equals("")) throw new UnauthorizedException("No authentication token found on request");
        return tokenValidator.parseToken(token).orElseThrow(InvalidTokenException::new);
    }
}
