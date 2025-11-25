package rg_oficina_backend.entity;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import rg_oficina_backend.dto.UsuarioDTO;
import rg_oficina_backend.entity.enums.TipoSituacaoUsuario;

/**
 * @author Gustavo Carvalho
 */
@Entity
@Table(name = "npl_usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String senha;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSituacaoUsuario situacao;
    
    public UsuarioEntity() {
    }
    
    public UsuarioEntity(UsuarioDTO usuario) {
        if(usuario != null) {
            this.id = usuario.getId();
            this.senha = usuario.getSenha();
            this.email = usuario.getEmail();
            this.situacao = usuario.getSituacao(); 
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public TipoSituacaoUsuario getSituacao() { return situacao; }
    public void setSituacao(TipoSituacaoUsuario situacao) { this.situacao = situacao; }

    @Override
    public int hashCode() { return Objects.hash(id); }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UsuarioEntity other = (UsuarioEntity) obj;
        return Objects.equals(id, other.id);
    }
}