package br.com.usermanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.jasypt.util.text.AES256TextEncryptor;

@Configuration
public class EncryptorConfig {
	
	@Value("${jasypt.password}")
	private String password;
	
	@Bean
	public AES256TextEncryptor  getEncryptor() {
		AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
		textEncryptor.setPassword(password);
		
		return textEncryptor;
	}

}
