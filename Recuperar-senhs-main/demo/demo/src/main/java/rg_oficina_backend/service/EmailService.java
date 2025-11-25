/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package rg_oficina_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gustavo Carvalho
 */

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmailTexto(String destinatario, String assunto, String mensagem) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(mensagem);

            javaMailSender.send(simpleMailMessage);

            // Adicionei este log para confirmar visualmente
            System.out.println("ENVIO DE EMAIL: Sucesso para " + destinatario);
            return "Email enviado";

        } catch(MailException e) {
            // AQUI ESTAVA O PROBLEMA: O erro acontecia mas ficava silêncioso.
            // Agora ele vai aparecer em vermelho no seu console.
            System.err.println("FALHA AO ENVIAR EMAIL: " + e.getMessage());
            // Mostra o rastro completo do erro
            return "Erro ao tentar enviar email " + e.getLocalizedMessage();
        }
    }
}