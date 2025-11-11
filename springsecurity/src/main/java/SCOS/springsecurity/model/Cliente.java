package SCOS.springsecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tb_clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente
{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;
    private String cpf_cnpj;
    private String email;
    private String telefone_fixo;
    private String telefone_celular;
    private String cep;
    private String estado;
    private String rua_numero;
    private String bairro;
    private Date data_cadastro;

}
