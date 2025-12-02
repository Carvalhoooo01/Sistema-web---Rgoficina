package rg_oficina_backend.repository;

import rg_oficina_backend.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findById(long id);

    // CORREÇÃO: Mudamos STR_TO_DATE (MySQL) para TO_DATE (PostgreSQL)
    // E o formato de '%d/%m/%Y' para 'DD/MM/YYYY'
    @Query("SELECT c FROM Cliente c WHERE TO_DATE(c.data_cadastro, 'DD/MM/YYYY') BETWEEN TO_DATE(:data_inicio, 'DD/MM/YYYY') AND TO_DATE(:data_fim, 'DD/MM/YYYY')")
    List<Cliente> findAllForRelatorio(@Param("data_inicio") String data_inicio, @Param("data_fim") String data_fim);

    @Query("SELECT c from Cliente c where c.nome = :info or c.cpf_cnpj = :info")
    Cliente findByInfo(@Param("info") String info);

    @Query("SELECT c FROM Cliente c WHERE c.cpf_cnpj = :cpf")
    Cliente findByCpf_cnpj(@Param("cpf") String cpf);

}