package rg_oficina_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rg_oficina_backend.model.OrdemServico;
import java.util.List;

@Repository
public interface OsRepository extends JpaRepository<OrdemServico, Long> {
    // Este método cria o SQL automaticamente: "SELECT * FROM ... WHERE status = ?"

}