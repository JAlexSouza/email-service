package br.com.emailmanager.core.usecase.service;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.com.kafkaserviceapi.config.KafkaTopic;
import br.com.emailmanager.core.domain.dto.EmailValidationDTO;
import br.com.emailmanager.core.domain.enums.EmailSubject;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class EmailService {
	
	private final String sender = "alexkb.souza@gmail.com";
	
	@Autowired
	private JavaMailSender javaMailSender;	
	@Autowired
    private SpringTemplateEngine templateEngine;
		
	@KafkaListener(topics =  KafkaTopic.EMAIL_VALIDATION, groupId = "email-service-group")
	public void listenEmailValidation(EmailValidationDTO emailValidationDTO) throws MessagingException {
		log.info("Recebido email de validação: {}", emailValidationDTO.getEmailDesnation());
		try {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
		
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setTo(emailValidationDTO.getEmailDesnation());
		mimeMessageHelper.setSubject(EmailSubject.VALIDATION.getSubject());
		mimeMessageHelper.setText(getValidationHtml(emailValidationDTO), true);
		
		log.info("Envio do email de validação para: {}", emailValidationDTO.getEmailDesnation());
		javaMailSender.send(message);				
		} catch (Exception e) {
			System.out.println("Erro no envio de email de validação");
		}
	}

	private String getValidationHtml(EmailValidationDTO emailValidationDTO) throws IOException {
		log.info("formatação do html do email de validação...");
		Map<String, Object> properties =  new HashMap<>();
		properties.put("destination", emailValidationDTO.getEmailDesnation());
		properties.put("textEncrypted", emailValidationDTO.getText());
		
		Context context = new Context();
		context.setVariables(properties);
		
		String html = templateEngine.process("validation", context);
		
		log.info("html construído...");
		return html;
	}

}
