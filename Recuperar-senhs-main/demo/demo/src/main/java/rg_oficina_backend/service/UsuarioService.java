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
 * Service de Usuários.
 * Responsável pelo ciclo de vida da conta: Cadastro, Criptografia, Ativação via Token e Recuperação.
 * @author Gustavo Carvalho
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioVerificadorRepository usuarioVerificadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeção do BCrypt para hash de senhas

    @Autowired
    private EmailService emailService; // Integração para envio de notificações

    // Lista usuários convertendo para DTO (Oculta a senha hashada na listagem)
    public List<UsuarioDTO> listarTodos(){
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(UsuarioDTO::new).toList();
    }

    // Inserção simples (Geralmente usada por Admins)
    public void inserir(UsuarioDTO usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
        // Criptografa a senha antes de persistir
        usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuarioEntity);
    }

    // CADASTRO COMPLETO (Fluxo Principal)
    public void inserirNovoUsuario(UsuarioDTO usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);

        // 1. Criptografia
        usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // Define status inicial (ATIVO para facilitar testes, mas preparado para PENDENTE)
        usuarioEntity.setSituacao(TipoSituacaoUsuario.ATIVO);

        usuarioEntity.setId(null);
        usuarioRepository.save(usuarioEntity);

        // 2. Geração de Token de Verificação (UUID)
        UsuarioVerificadorEntity verificador = new UsuarioVerificadorEntity();
        verificador.setUsuario(usuarioEntity);
        verificador.setUuid(UUID.randomUUID());
        verificador.setDataExpiracao(Instant.now().plusMillis(900000)); // Expira em 15 minutos
        usuarioVerificadorRepository.save(verificador);

        // 3. Envio de E-mail
        try {
            emailService.enviarEmailTexto(usuario.getEmail(),
                    "Novo usuário cadastrado",
                    "Cadastro realizado! Bem-vindo ao Oficina RG.");
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail (ignorado): " + e.getMessage());
        }
    }

    // Valida o link clicado no e-mail
    public String verificarCadastro(String uuid) {

        UsuarioVerificadorEntity usuarioVerificacao = usuarioVerificadorRepository.findByUuid(UUID.fromString(uuid)).orElse(null);

        if(usuarioVerificacao != null) {
            // Verifica se o token ainda é válido (não expirou)
            if(usuarioVerificacao.getDataExpiracao().compareTo(Instant.now()) >= 0) {

                UsuarioEntity u = usuarioVerificacao.getUsuario();
                u.setSituacao(TipoSituacaoUsuario.ATIVO); // Ativa o usuário definitivamente

                usuarioRepository.save(u);
                return "Usuário Verificado com Sucesso";
            }else {
                // Se expirou, limpa o token
                usuarioVerificadorRepository.delete(usuarioVerificacao);
                return "Tempo de verificação expirado";
            }
        }else {
            return "Token inválido ou usuário não encontrado";
        }
    }

    // Atualiza dados cadastrais e recriptografa a senha se ela for alterada
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

    // Processo de recuperação de conta
    public String recuperarSenha(String email) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario == null) {
            return "E-mail não encontrado no sistema.";
        }

        // Em um cenário real, geraríamos uma nova senha aleatória aqui.
        // Para a apresentação, simulamos o envio de instruções.
        try {
            emailService.enviarEmailTexto(usuario.getEmail(),
                    "Recuperação de Senha - Oficina RG",
                    "Recebemos sua solicitação de recuperação de senha.\nSua senha é: " + usuario.getSenha());
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail de recuperação: " + e.getMessage());
            return "Erro ao enviar e-mail. Contate o suporte.";
        }

        return "Instruções enviadas para o seu e-mail.";
    }
}