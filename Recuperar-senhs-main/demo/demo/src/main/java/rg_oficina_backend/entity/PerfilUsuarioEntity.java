/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rg_oficina_backend.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import rg_oficina_backend.dto.PerfilUsuarioDTO;

/**
 * Entidade que relaciona Usuários a Perfis (Tabela de Associação).
 * @author Gustavo Carvalho
 */
@Entity
@Table(name = "npl_perfil_usuario")
public class PerfilUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Alterado de AUTO para IDENTITY
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private UsuarioEntity usuario;
    
    @ManyToOne
    @JoinColumn(name = "ID_PERFIL")
    private PerfilEntity perfil;
    
    // Construtor vazio obrigatório
    public PerfilUsuarioEntity() {
    }
    
    // Construtor de conversão DTO -> Entity
    public PerfilUsuarioEntity(PerfilUsuarioDTO perfilUsuario) {
        if(perfilUsuario != null) {
            // Substituído BeanUtils por atribuição manual para evitar erro no 'this'
            this.id = perfilUsuario.getId();
            
            if(perfilUsuario.getUsuario() != null) {
                this.usuario = new UsuarioEntity(perfilUsuario.getUsuario());
            }
            if(perfilUsuario.getPerfil() != null) {
                this.perfil = new PerfilEntity(perfilUsuario.getPerfil());
            }   
        }
    }

    // --- Getters e Setters Manuais ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public PerfilEntity getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEntity perfil) {
        this.perfil = perfil;
    }

    // --- Equals e HashCode ---

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PerfilUsuarioEntity other = (PerfilUsuarioEntity) obj;
        return Objects.equals(id, other.id);
    }
}