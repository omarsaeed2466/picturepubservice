package omar.spring.pps.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
    UserDetails loadUserByUsername(String username);
    void save(User user);
    void saveToDefaults(User user);

    void saveToDefaults(omar.spring.pps.data.entities.User toUser);
}
