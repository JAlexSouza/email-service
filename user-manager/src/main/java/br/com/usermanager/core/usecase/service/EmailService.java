package br.com.usermanager.core.usecase.service;

import br.com.kafkaserviceapi.config.KafkaTopic;
import br.com.usermanager.core.model.dto.EmailValidationDTO;
import br.com.usermanager.core.model.entity.UserKey;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
	
	@Autowired
	private KafkaTemplate<String, EmailValidationDTO> kafkaTemplate;
	
	@Autowired
	private AES256TextEncryptor textEncryptor;
	
	public void emailSender(UserKey userKey) {
		log.info("Montando dados do email de validação...");
		EmailValidationDTO emailValidationDTO = EmailValidationDTO.builder()
																	.emailDesnation(userKey.getUser().getEmail())
																	.text(emailValidationEncryption(userKey))
																	.build();
		log.info("Dados enviado para servico de email...");
		kafkaTemplate.send(KafkaTopic.EMAIL_VALIDATION, emailValidationDTO);
	}

	private String emailValidationEncryption(UserKey userKey) {
		return textEncryptor.encrypt(userKey.getKeySecrete().toString() + userKey.getRegistrationDate().toString());
	}
}
