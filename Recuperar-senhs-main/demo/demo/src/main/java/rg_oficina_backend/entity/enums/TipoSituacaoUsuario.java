/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rg_oficina_backend.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeração para a situação do usuário.
 * @author Gustavo Carvalho
 */
public enum TipoSituacaoUsuario { // Corrigido de 'class' para 'enum'

    ATIVO ("A", "Ativo"),
    INATIVO ("I", "Inativo"),
    PENDENTE ("P", "Pendente");
    
    private String codigo;
    private String descricao;
    
    private TipoSituacaoUsuario(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    @JsonCreator
    public static TipoSituacaoUsuario doValor(String codigo) {
        if (codigo == null) {
            return null;
        }

        // Percorre todos os valores do Enum automaticamente
        for (TipoSituacaoUsuario tipo : values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        
        return null; // Ou lançar exceção: throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}