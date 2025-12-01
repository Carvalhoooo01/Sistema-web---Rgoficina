package rg_oficina_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Serviço de E-mail (Infraestrutura).
 * Responsável pela comunicação SMTP para envio de notificações, boas-vindas e recuperação de senha.
 * @author Gustavo Carvalho
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender; // O "carteiro" do Spring

    // Lê o e-mail configurado no application.properties para usar como remetente
    @Value("${spring.mail.username}")
    private String remetente;

    /**
     * Envia um e-mail de texto simples.
     * @param destinatario O e-mail de quem vai receber.
     * @param assunto O título do e-mail.
     * @param mensagem O corpo do texto.
     * @return String indicando sucesso ou erro (para feedback na API).
     */
    public String enviarEmailTexto(String destinatario, String assunto, String mensagem) {
        try {
            // Monta o objeto da mensagem
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(mensagem);

            // Realiza o envio efetivo (conecta no servidor SMTP)
            javaMailSender.send(simpleMailMessage);

            // Log de sucesso no console do servidor
            System.out.println("ENVIO DE EMAIL: Sucesso para " + destinatario);
            return "Email enviado";

        } catch(MailException e) {
            // Tratamento de falhas de rede ou autenticação SMTP
            // System.err imprime em vermelho no console, facilitando a visualização do erro
            System.err.println("FALHA AO ENVIAR EMAIL: " + e.getMessage());

            // Retorna o erro detalhado (em produção, evitaríamos mostrar o erro técnico para o usuário final)
            return "Erro ao tentar enviar email " + e.getLocalizedMessage();
        }
    }
}