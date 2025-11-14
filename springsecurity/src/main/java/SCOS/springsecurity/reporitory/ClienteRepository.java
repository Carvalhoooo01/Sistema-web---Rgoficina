package SCOS.springsecurity.reporitory;

import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.model.OS;
import SCOS.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>
{

    Cliente findById(long id);

    @Query("SELECT c FROM Cliente c WHERE STR_TO_DATE(c.data_cadastro, '%d/%m/%Y') BETWEEN STR_TO_DATE(:data_inicio, '%d/%m/%Y') AND STR_TO_DATE(:data_fim, '%d/%m/%Y')")
    List<Cliente> findAllForRelatorio(@Param("data_inicio") String data_inicio, @Param("data_fim") String data_fim);

}
