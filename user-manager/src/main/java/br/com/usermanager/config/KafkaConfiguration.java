package br.com.usermanager.config;

import org.springframework.context.annotation.Configuration;

import br.com.kafkaserviceapi.config.KafkaConfig;
import br.com.usermanager.core.model.dto.UserKeyDTO;

@Configuration
public class KafkaConfiguration extends KafkaConfig<UserKeyDTO>{

}
