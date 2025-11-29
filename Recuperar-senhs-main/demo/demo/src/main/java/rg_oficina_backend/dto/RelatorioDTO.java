package rg_oficina_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

// Usamos @JsonFormat para garantir que o Java entenda a data vinda do JSON (aaaa-mm-dd)
public record RelatorioDTO(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Sao_Paulo")
        Date data_inicio,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Sao_Paulo")
        Date data_final
) {}