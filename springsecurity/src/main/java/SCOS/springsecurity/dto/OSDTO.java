package SCOS.springsecurity.dto;

import SCOS.springsecurity.model.Cliente;

public record OSDTO(Cliente cliente_id,
String cpf_cnpj,
String endereco,
String telefone_celular,
String telefone_fixo,
String tipo,
String modelo,
String marca,
String n_serial,
String descricao,
String prioridade) {
}
