package netology.ru.diplom.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.postgresql.shaded.com.ongres.scram.common.ScramAttributes;
import org.springframework.security.core.Authentication;
import netology.ru.diplom.entities.User;
import netology.ru.diplom.model.SecurityUser;
import netology.ru.diplom.security.JWTUtil;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.postgresql.PGProperty.PASSWORD;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private JWTUtil jwtTokenUtils;
    private final String token = UUID.randomUUID().toString();
    private final SecurityUser user = new SecurityUser(new User(ScramAttributes.USERNAME, PASSWORD, null));

    @Test
    void loginUserTest() {
        final Authentication authentication = mock(Authentication.class);
        given(jwtTokenUtils.generateToken(String.valueOf(authentication))).willReturn(token);
        given((SecurityUser) authentication.getPrincipal()).willReturn(user);

        assertEquals(token, authService.loginUser(authentication));
    }
}
