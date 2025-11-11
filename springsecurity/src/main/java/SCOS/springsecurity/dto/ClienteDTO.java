package SCOS.springsecurity.dto;

import java.time.LocalDate;
import java.util.Date;

public record ClienteDTO(String nome,
    String cpf_cnpj,
    String email,
    String telefone_fixo,
    String telefone_celular,
    String cep,
    String estado,
    String rua_numero,
    String bairro)
{
}
