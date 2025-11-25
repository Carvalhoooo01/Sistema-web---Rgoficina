package rg_oficina_backend.dto;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rg_oficina_backend.entity.PerfilEntity;

/**
 *
 * @author Gustavo Carvalho
 */

@Getter
@Setter
@NoArgsConstructor
public class PerfilDTO {

    private Long id;
	private String descricao;
	
	public PerfilDTO(PerfilEntity perfil) {
		BeanUtils.copyProperties(perfil, this);
	}
}
