package br.com.emailmanager.core.usecase;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.kafkaserviceapi.config.KafkaTopic;
import br.com.emailmanager.core.domain.dto.EmailValidationDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {
	
	@KafkaListener(topics =  KafkaTopic.EMAIL_VALIDATION, groupId = "email-service-group")
	public void lisenEmailValidation(EmailValidationDTO emailValidationDTO) {
		log.info("Recebido email de validação: {}", emailValidationDTO.getEmailDesnation());
		
		try {
			System.out.println("email recebido");
		} catch (Exception e) {
			System.out.println("Erro no envio de email de validação");
		}
	}

}
