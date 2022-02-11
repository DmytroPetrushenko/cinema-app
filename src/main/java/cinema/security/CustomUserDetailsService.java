package cinema.security;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }
        User user = optionalUser.get();
        UserBuilder userBuilder = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail());
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getRoles().stream().map(Role::getRole).toArray(String[]::new));
        return userBuilder.build();
    }
}
