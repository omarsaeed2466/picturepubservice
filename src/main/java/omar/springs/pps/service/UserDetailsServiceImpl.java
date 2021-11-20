package omar.spring.pps.service;

import lombok.extern.slf4j.Slf4j;
import omar.spring.pps.data.entities.Role;
import omar.spring.pps.data.entities.Roleenum;
import omar.spring.pps.data.repositories.RoleRepository;
import omar.spring.pps.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import omar.spring.pps.data.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
       log.info("find by username" +username);

        return userRepository.findDistinctByUsername(username);
    }


    public void save(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       Role role = roleRepository.findDistinctByName(Roleenum.ROLE_USER.name());
        if (role!=null)
            user.setRoles(Set.of(role));

    }
    @Transactional

    public void saveToDefaults(User user) {
        user.setPassword (bCryptPasswordEncoder.encode(user.getPassword()));

        Role role= roleRepository.findDistinctByName(Roleenum.ROLE_USER.name());
        if (role!=null)
            user.setRoles(Set.of(role));

        System.out.println("xxx "+ Objects.requireNonNull(role).getName());
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        userRepository.save(user);
    }
}
