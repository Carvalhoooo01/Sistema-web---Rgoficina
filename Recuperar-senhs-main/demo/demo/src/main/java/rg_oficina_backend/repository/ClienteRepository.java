package rg_oficina_backend.repository;

import rg_oficina_backend.entity.Cliente;
import rg_oficina_backend.entity.OS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>
{

    Cliente findById(long id);

    @Query("SELECT c FROM Cliente c WHERE STR_TO_DATE(c.data_cadastro, '%d/%m/%Y') BETWEEN STR_TO_DATE(:data_inicio, '%d/%m/%Y') AND STR_TO_DATE(:data_fim, '%d/%m/%Y')")
    List<Cliente> findAllForRelatorio(@Param("data_inicio") String data_inicio, @Param("data_fim") String data_fim);

    @Query("SELECT c from Cliente c where c.nome = :info or c.cpf_cnpj = :info")
    Cliente findByInfo(@Param("info") String info);

}
