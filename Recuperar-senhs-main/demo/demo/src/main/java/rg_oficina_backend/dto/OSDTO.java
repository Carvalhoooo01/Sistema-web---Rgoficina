package rg_oficina_backend.dto;

public record OSDTO(
        Long clienteId, // <--- MUDAMOS AQUI: De "Cliente" para "Long"
        String tipo,
        String modelo,
        String marca,
        String n_serial,
        String descricao,
        String prioridade
) {}