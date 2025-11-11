package SCOS.springsecurity.reporitory;

import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>
{

    Cliente findById(long id);

}
