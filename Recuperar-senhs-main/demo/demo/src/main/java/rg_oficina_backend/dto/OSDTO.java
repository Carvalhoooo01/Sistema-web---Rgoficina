package rg_oficina_backend.dto;

public record OSDTO(
        Long cliente_id, // <--- MUDAMOS AQUI: De "Cliente" para "Long"
        String tipo,
        String modelo,
        String marca,
        String n_serial,
        String descricao,
        String prioridade
) {}