package rg_oficina_backend.dto;

import rg_oficina_backend.entity.Cliente;

public record OSDTO(Long cliente_id,
                    String tipo,
                    String modelo,
                    String marca,
                    String n_serial,
                    String descricao,
                    String prioridade)
{
}
