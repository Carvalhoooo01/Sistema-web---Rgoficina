package SCOS.springsecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tb_os")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OS
{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @ManyToOne
    private Cliente cliente_id;

    private String cpf_cnpj;
    private String endereco;
    private String telefone_celular;
    private String telefone_fixo;
    private String tipo;
    private String modelo;
    private String marca;
    private String n_serial;
    private String descricao;
    private String prioridade;
    private String data_abertura;


    /*cliente_id: _infos[0],
            cpf_cnpj: _infos[1],
            endereco: _infos[2],
            telefone_celular: _infos[3],
            telefone_fixo: _infos[4],
            tipo: _infos[5],
            modelo: _infos[6],
            marca: _infos[7],
            n_serial: _infos[8],
            descricao: _infos[9],
            prioridade: _infos[10],
            data_abertura: new Date().toLocaleDateString()*/

}
