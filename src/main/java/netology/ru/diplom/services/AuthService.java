package netology.ru.diplom.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import netology.ru.diplom.entities.User;
import netology.ru.diplom.model.SecurityUser;
import netology.ru.diplom.security.JWTUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JWTUtil jwtTokenUtils;
    private final Map<String, String> tokenStore = new HashMap<>();

    public AuthService() {
        this(null);
    }

    public AuthService(JWTUtil JWTUtil) {

        this.jwtTokenUtils = JWTUtil;
    }

    public String loginUser(Authentication authentication) {
        try {
            SecurityUser user = (SecurityUser) authentication.getPrincipal();
            String login = user.getUsername();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = JWTUtil.generateToken(String.valueOf(authentication));
            tokenStore.put(token, login);
            return token;
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    public void logoutUser(String authToken) {
        tokenStore.remove(authToken);
    }
}
