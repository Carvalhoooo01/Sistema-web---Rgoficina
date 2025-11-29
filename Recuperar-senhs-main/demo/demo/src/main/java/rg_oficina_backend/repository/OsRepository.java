package rg_oficina_backend.repository;

import rg_oficina_backend.entity.OS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OSRepository extends JpaRepository<OS, Long> {

    OS findById(long id);

    // CORREÇÃO: Mudamos de STR_TO_DATE (MySQL) para TO_DATE (PostgreSQL)
    // E mudamos o formato de '%d/%m/%Y' para 'DD/MM/YYYY'
    @Query("SELECT o FROM OS o WHERE TO_DATE(o.data_abertura, 'DD/MM/YYYY') BETWEEN TO_DATE(:data_inicio, 'DD/MM/YYYY') AND TO_DATE(:data_fim, 'DD/MM/YYYY')")
    List<OS> findAllForRelatorio(@Param("data_inicio") String data_inicio, @Param("data_fim") String data_fim);

}