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
import rg_oficina_backend.dto.PermissaoPerfilRecursoDTO;

/**
 * Entidade que relaciona Perfis a Recursos (Tabela de Permissões).
 * @author Gustavo Carvalho
 */
@Entity
@Table(name = "npl_permissao_perfil_recurso")
public class PermissaoPerfilRecursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ajustado para IDENTITY
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_PERFIL")
    private PerfilEntity perfil;
    
    @ManyToOne
    @JoinColumn(name = "ID_RECURSO")
    private RecursoEntity recurso;
    
    // Construtor vazio obrigatório
    public PermissaoPerfilRecursoEntity() {
    }
    
    // Construtor de conversão DTO -> Entity
    public PermissaoPerfilRecursoEntity(PermissaoPerfilRecursoDTO permissaoPerfilRecurso) {
        if(permissaoPerfilRecurso != null) {
            // Atribuição manual segura (substituindo BeanUtils e 'this')
            this.id = permissaoPerfilRecurso.getId();
            
            if(permissaoPerfilRecurso.getRecurso() != null) {
                this.recurso = new RecursoEntity(permissaoPerfilRecurso.getRecurso());
            }
            if(permissaoPerfilRecurso.getPerfil() != null) {
                this.perfil = new PerfilEntity(permissaoPerfilRecurso.getPerfil());
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

    public PerfilEntity getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEntity perfil) {
        this.perfil = perfil;
    }

    public RecursoEntity getRecurso() {
        return recurso;
    }

    public void setRecurso(RecursoEntity recurso) {
        this.recurso = recurso;
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
        PermissaoPerfilRecursoEntity other = (PermissaoPerfilRecursoEntity) obj;
        return Objects.equals(id, other.id);
    }
}