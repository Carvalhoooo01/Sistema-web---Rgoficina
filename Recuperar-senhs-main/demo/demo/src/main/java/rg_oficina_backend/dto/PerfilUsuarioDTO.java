package rg_oficina_backend.dto;

// REMOVIDO: import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rg_oficina_backend.entity.PerfilUsuarioEntity;

/**
 *
 * @author Gustavo Carvalho
 */

@Getter
@Setter
@NoArgsConstructor
public class PerfilUsuarioDTO {

    private Long id;
    private UsuarioDTO usuario;
    private PerfilDTO perfil;

    // CONSTRUTOR CORRIGIDO: Atribuição manual (Remove o aviso do 'this')
    public PerfilUsuarioDTO(PerfilUsuarioEntity perfilUsuario) {
        if(perfilUsuario != null) {
            this.id = perfilUsuario.getId(); // Atribuição manual do ID
            
            if(perfilUsuario.getUsuario() != null) {
                this.usuario = new UsuarioDTO(perfilUsuario.getUsuario());
            }
            
            if(perfilUsuario.getPerfil() != null) {
                this.perfil = new PerfilDTO(perfilUsuario.getPerfil());
            }       
        }
    }
}