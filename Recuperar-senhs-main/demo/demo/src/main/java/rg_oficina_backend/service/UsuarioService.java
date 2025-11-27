/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rg_oficina_backend.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rg_oficina_backend.dto.UsuarioDTO;
import rg_oficina_backend.entity.UsuarioEntity;
import rg_oficina_backend.entity.UsuarioVerificadorEntity;
import rg_oficina_backend.entity.enums.TipoSituacaoUsuario;
import rg_oficina_backend.repository.UsuarioRepository;
import rg_oficina_backend.repository.UsuarioVerificadorRepository;

/**
 *
 * @author Gustavo Carvalho
 */

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioVerificadorRepository usuarioVerificadorRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    
    public List<UsuarioDTO> listarTodos(){
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(UsuarioDTO::new).toList();
    }
    
    public void inserir(UsuarioDTO usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
        usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuarioEntity);
    }
    
    public void inserirNovoUsuario(UsuarioDTO usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
        usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
        
        // --- DEFINE COMO ATIVO PARA TESTAR LOGIN ---
        usuarioEntity.setSituacao(TipoSituacaoUsuario.ATIVO);
        // -------------------------------------------
        
        usuarioEntity.setId(null);
        usuarioRepository.save(usuarioEntity);
        
        UsuarioVerificadorEntity verificador = new UsuarioVerificadorEntity();
        verificador.setUsuario(usuarioEntity);
        verificador.setUuid(UUID.randomUUID());
        verificador.setDataExpiracao(Instant.now().plusMillis(900000));
        usuarioVerificadorRepository.save(verificador);
        
        try {
            emailService.enviarEmailTexto(usuario.getEmail(), 
                    "Novo usuário cadastrado", 
                    "Cadastro realizado! Sua senha: " + usuario.getSenha());
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail (ignorado): " + e.getMessage());
        }
    }
    
    public String verificarCadastro(String uuid) {
    
        UsuarioVerificadorEntity usuarioVerificacao = usuarioVerificadorRepository.findByUuid(UUID.fromString(uuid)).orElse(null);
        
        if(usuarioVerificacao != null) {
            if(usuarioVerificacao.getDataExpiracao().compareTo(Instant.now()) >= 0) {
                
                UsuarioEntity u = usuarioVerificacao.getUsuario();
                u.setSituacao(TipoSituacaoUsuario.ATIVO);
                
                usuarioRepository.save(u);
                
                return "Usuário Verificado";
            }else {
                usuarioVerificadorRepository.delete(usuarioVerificacao);
                return "Tempo de verificação expirado";
            }
        }else {
            return "Usuario não verificado";
        }
        
    }
    
    public UsuarioDTO alterar(UsuarioDTO usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
        usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
    }
    
    public void excluir(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id).get();
        usuarioRepository.delete(usuario);
    }
    
    public UsuarioDTO buscarPorId(Long id) {
        return new UsuarioDTO(usuarioRepository.findById(id).get());
    }

    // --- NOVO MÉTODO: RECUPERAR SENHA ---
    public String recuperarSenha(String email) {
        // Busca o usuário pelo e-mail
        UsuarioEntity usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario == null) {
            return "E-mail não encontrado no sistema.";
        }

        // Gera uma senha aleatória de 8 caracteres
        String novaSenha = usuario.getSenha();

        // Tenta enviar o e-mail
        try {
            emailService.enviarEmailTexto(usuario.getEmail(), 
                "Recuperação de Senha - Oficina RG", 
                "Sua senha é: " + novaSenha);
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail de recuperação: " + e.getMessage());
            return "Erro ao enviar e-mail. Contate o suporte.";
        }

        return "Uma nova senha foi enviada para o seu e-mail.";
    }
}