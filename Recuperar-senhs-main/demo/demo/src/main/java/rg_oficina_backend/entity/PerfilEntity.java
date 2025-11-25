/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rg_oficina_backend.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import rg_oficina_backend.dto.PerfilDTO;

/**
 * Entidade que representa os perfis de acesso (Roles).
 * @author Gustavo Carvalho
 */
@Entity
@Table(name = "npl_perfil")
public class PerfilEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Alterado de AUTO para IDENTITY (Padrão Postgres)
    private Long id;
    
    @Column(nullable = false)
    private String descricao;
    
    // Construtor vazio obrigatório para o JPA
    public PerfilEntity() {
    }

    // Construtor de conversão DTO -> Entity
    public PerfilEntity(PerfilDTO perfil) {
        if(perfil != null) {
            this.id = perfil.getId();
            this.descricao = perfil.getDescricao();
        }
    }

    // --- Getters e Setters Manuais ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // --- Equals e HashCode (Baseado no ID) ---

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
        PerfilEntity other = (PerfilEntity) obj;
        return Objects.equals(id, other.id);
    }
}