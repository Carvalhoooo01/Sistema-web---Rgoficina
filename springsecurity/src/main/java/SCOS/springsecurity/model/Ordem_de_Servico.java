package SCOS.springsecurity.model;

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
public class Ordem_de_Servico
{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

}
