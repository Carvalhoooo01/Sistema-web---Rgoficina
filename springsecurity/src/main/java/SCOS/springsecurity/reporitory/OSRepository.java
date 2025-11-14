package SCOS.springsecurity.reporitory;

import SCOS.springsecurity.model.Cliente;
import SCOS.springsecurity.model.OS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OSRepository extends JpaRepository<OS, Long>
{

    OS findById(long id);

    @Query("SELECT o FROM OS o WHERE STR_TO_DATE(o.data_abertura, '%d/%m/%Y') BETWEEN STR_TO_DATE(:data_inicio, '%d/%m/%Y') AND STR_TO_DATE(:data_fim, '%d/%m/%Y')")
    List<OS> findAllForRelatorio(@Param("data_inicio") String data_inicio, @Param("data_fim") String data_fim);

}
