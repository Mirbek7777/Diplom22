package netology.ru.diplom.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import netology.ru.diplom.cloudstorage.entity.User;
import netology.ru.diplom.cloudstorage.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;


public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    private final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

    public MyUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<User> userRes = userRepo.findByLogin(login);

        if (userRes.isEmpty()) {
            log.error("User with username: {} not found", userRes);
            throw new UsernameNotFoundException("User with username: " + userRes + " not found");
        }
        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                login,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority())));
    }

    public static User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return new User.Builder()
                .withLogin(userDetails.getUsername())
                .withAuthority(String.valueOf(userDetails.getAuthorities()))
                .withPassword(userDetails.getPassword())
                .withEnabled(userDetails.isEnabled())
                .build();
    }

    private static class User {
    }

    private class UserRepository {
        public Optional<User> findByLogin(String login) {
            return null;
        }

    }
}
