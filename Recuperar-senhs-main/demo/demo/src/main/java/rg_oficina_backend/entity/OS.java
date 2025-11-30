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

    // Relacionamento com Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id") // Boa prática: define o nome da coluna no banco
    private Cliente cliente_id;

    // Dados da Máquina
    private String tipo;    // Ex: Geladeira
    private String modelo;  // Ex: Frost Free
    private String marca;   // Ex: Brastemp
    private String n_serial;

    private String descricao;
    private String prioridade;
    private String data_abertura;

    // --- REMOVI OS MÉTODOS "getCliente" QUE DAVAM ERRO ---
    // O Lombok (@Getter) já cria automaticamente:
    // getCliente_id(), getTipo(), getMarca(), etc.
}