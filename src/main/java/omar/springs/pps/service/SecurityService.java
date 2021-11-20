package omar.spring.pps.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
    UserDetailsService userService();
}
