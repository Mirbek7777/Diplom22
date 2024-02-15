package netology.ru.diplom.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import netology.ru.diplom.cloudstorage.repository.UserRepository;
import netology.ru.diplom.cloudstorage.security.JWTFilter;
import netology.ru.diplom.cloudstorage.security.JWTUtil;
import netology.ru.diplom.cloudstorage.security.MyUserDetailsService;

/**
 * @author VladSemikin
 */

@Configuration
public class CloudStorageConfiguration {

    @Bean
    public JWTFilter jwtFilter(UserDetailsService userDetailsService) {
        return new JWTFilter(jwtUtil(userDetailsService));
    }

    @Bean
    public JWTUtil jwtUtil(UserDetailsService userDetailsService) {
        return new JWTUtil(userDetailsService);
    }

    @Bean
    public MyUserDetailsService myUserDetailsService(UserRepository userRepository){
        return new MyUserDetailsService(userRepository);
    }

    private class JWTFilter {
    }

    private class JWTUtil {
        public JWTUtil(Object userDetailsService) {

        }
    }

    private class MyUserDetailsService {
    }

    private class UserRepository {
    }
}
