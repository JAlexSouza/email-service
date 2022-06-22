package br.com.emailmanager.config;

import org.springframework.context.annotation.Configuration;

import br.com.emailmanager.core.domain.dto.EmailValidationDTO;
import br.com.kafkaserviceapi.config.KafkaConfig;

@Configuration
public class KafkaConfiguration extends KafkaConfig<EmailValidationDTO>{

	public KafkaConfiguration() {
		super(EmailValidationDTO.class);
	}
}
