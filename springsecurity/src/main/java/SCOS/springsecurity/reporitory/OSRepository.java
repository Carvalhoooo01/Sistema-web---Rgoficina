package SCOS.springsecurity.reporitory;

import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.model.OS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OSRepository extends JpaRepository<OS, Long>
{

    OS findById(long id);

}
