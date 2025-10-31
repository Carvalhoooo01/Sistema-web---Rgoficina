package SCOS.springsecurity.reporitory;

import SCOS.springsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {


    User findByEmail(String email);
    UserDetails findByLogin(String login);

}
