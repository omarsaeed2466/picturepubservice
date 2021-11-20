package omar.spring.pps.DTO;

import omar.spring.pps.data.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public User toUser (UserDTO input) {
      User user = new User();
      user.setUsername(input.getUsername());
      user.setPassword(input.getPassword());
      return user;
  }
  }

