package rg_oficina_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tb_ordens_de_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cliente;

    @Column(nullable = false)
    private String maquina;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String prioridade;

    // --- Construtores ---

    public OrdemServico() {
    }

    public OrdemServico(String cliente, String maquina, String descricao, String prioridade) {
        this.cliente = cliente;
        this.maquina = maquina;
        this.descricao = descricao;
        this.prioridade = prioridade;
    }
}