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
import rg_oficina_backend.dto.RecursoDTO;

/**
 * Entidade que representa um Recurso do sistema (para controle de acesso).
 * @author Gustavo Carvalho
 */
@Entity
@Table(name = "npl_recurso")
public class RecursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ajustado para IDENTITY
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String chave;
    
    // Construtor vazio obrigatório
    public RecursoEntity() {
    }

    // Construtor de conversão DTO -> Entity
    public RecursoEntity(RecursoDTO recurso) {
        if(recurso != null) {
            // Atribuição manual segura (substituindo BeanUtils e 'this')
            this.id = recurso.getId();
            this.nome = recurso.getNome();
            this.chave = recurso.getChave();
        }
    }

    // --- Getters e Setters Manuais ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
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
        RecursoEntity other = (RecursoEntity) obj;
        return Objects.equals(id, other.id);
    }
}