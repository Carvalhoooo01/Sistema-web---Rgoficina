package rg_oficina_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_os")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente_id;

    private String tipo;
    private String modelo;
    private String marca;
    private String n_serial;

    // --- CORREÇÃO AQUI ---
    // columnDefinition = "TEXT" avisa ao banco que este campo aceita textos gigantes
    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String prioridade;
    private String data_abertura;

}