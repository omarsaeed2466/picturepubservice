package omar.spring.pps.data.repositories;

import omar.spring.pps.data.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findDistinctByName(String name);
}
