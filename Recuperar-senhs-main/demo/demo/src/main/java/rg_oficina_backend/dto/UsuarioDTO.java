package rg_oficina_backend.dto;

import rg_oficina_backend.entity.UsuarioEntity;
import rg_oficina_backend.entity.enums.TipoSituacaoUsuario;

/**
 * Data Transfer Object para Usuários.
 * @author Gustavo Carvalho
 */
public class UsuarioDTO {

    private Long id;
    private String email;
    private String senha;
    private TipoSituacaoUsuario situacao;

    // Construtor vazio
    public UsuarioDTO() {
    }

    // Construtor que converte Entidade -> DTO (Essencial para o método alterar/listar retornar dados)
    public UsuarioDTO(UsuarioEntity entity) {
        if(entity != null) {
            this.id = entity.getId();
            this.email = entity.getEmail();
            this.senha = entity.getSenha();
            this.situacao = entity.getSituacao();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoSituacaoUsuario getSituacao() {
        return situacao;
    }

    public void setSituacao(TipoSituacaoUsuario situacao) {
        this.situacao = situacao;
    }
}