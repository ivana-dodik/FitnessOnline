package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private final LogsService logsService;
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ivanadodikid@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        logsService.addLog("Sending e-mail to: " + to);
        emailSender.send(message);
    }

}
