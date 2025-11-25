package rg_oficina_backend.dto;

// REMOVIDO: import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rg_oficina_backend.entity.PermissaoPerfilRecursoEntity;

/**
 *
 * @author Gustavo Carvalho
 */

@Getter
@Setter
@NoArgsConstructor
public class PermissaoPerfilRecursoDTO {

    private Long id;
    private PerfilDTO perfil;   
    private RecursoDTO recurso;
    
    // CONSTRUTOR CORRIGIDO: Atribuição manual (Remove o aviso do 'this')
    public PermissaoPerfilRecursoDTO(PermissaoPerfilRecursoEntity permissaoPerfilRecurso) {
        if(permissaoPerfilRecurso != null) {
            this.id = permissaoPerfilRecurso.getId(); // Atribuição manual do ID
            
            if(permissaoPerfilRecurso.getRecurso() != null) {
                this.recurso = new RecursoDTO(permissaoPerfilRecurso.getRecurso());
            }
            
            if(permissaoPerfilRecurso.getPerfil() != null) {
                this.perfil = new PerfilDTO(permissaoPerfilRecurso.getPerfil());
            }       
        }
    }
}