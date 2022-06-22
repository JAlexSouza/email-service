package br.com.usermanager.core.usecase.service;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.kafkaserviceapi.config.KafkaTopic;
import br.com.usermanager.core.model.dto.EmailValidationDTO;
import br.com.usermanager.core.model.entity.UserKey;

@Service
public class EmailService {
	
	@Autowired
	private KafkaTemplate<String, EmailValidationDTO> kafkaTemplate;
	
	@Autowired
	private AES256TextEncryptor textEncryptor;
	
	public void emailSender(UserKey userKey) {
		
		EmailValidationDTO emailValidationDTO = EmailValidationDTO.builder()
																		.emailDesnation(userKey.getUser().getEmail())
																		.text(emailValidationEncryption(userKey))
																		.build();
		
		kafkaTemplate.send(KafkaTopic.EMAIL_VALIDATION, emailValidationDTO);
		
	}

	private String emailValidationEncryption(UserKey userKey) {
		return textEncryptor.encrypt(userKey.getKeySecrete().toString() + userKey.getRegistrationDate().toString());
	}

}
