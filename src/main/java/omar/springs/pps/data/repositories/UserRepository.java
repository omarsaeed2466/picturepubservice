package omar.spring.pps.data.repositories;

import omar.spring.pps.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User  findByUsername(String username);
    User findDistinctByUsername(String username);

}
