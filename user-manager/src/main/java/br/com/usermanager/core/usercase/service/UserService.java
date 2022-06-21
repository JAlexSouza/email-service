package br.com.usermanager.core.usercase.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.usermanager.core.model.dto.UserDTO;
import br.com.usermanager.core.model.dto.UserKeyDTO;
import br.com.usermanager.core.model.entity.User;
import br.com.usermanager.core.model.entity.UserKey;
import br.com.usermanager.core.model.enums.UserStatus;
import br.com.usermanager.core.usercase.contracts.UserKeyRepository;
import br.com.usermanager.core.usercase.contracts.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserKeyRepository userKeyRepository;

	@Autowired
	private KafkaTemplate<String, UserKeyDTO> kafkaTemplate;

	public ResponseEntity<String> resgiterUser(UserDTO userDTO) {
		try {
			User user = userRepository.findByEmail(userDTO.getEmail());

			if (Objects.isNull(user)) {

				user = User.builder()
						.email(userDTO.getEmail())
						.password(userDTO.getPassword())
						.name(userDTO.getName())
						.lastName(userDTO.getLastName())
						.status(UserStatus.PEDDING)
						.build();

				userRepository.save(user);

				UserKey userKey = UserKey.builder().user(user).keySecrete(UUID.randomUUID().toString())
						.registrationDate(LocalDate.now()).build();

				UserKeyDTO userKeyDTO = UserKeyDTO.convert(userKeyRepository.save(userKey));

				kafkaTemplate.send("EMAIL_VALIDATION", "user-manager", userKeyDTO);
			} else {
				kafkaTemplate.send("EMAIL_VALIDATION", "user-manager", UserKeyDTO.convert(user.getUserKey()));
			}			
			return new ResponseEntity<String>("Cadastro efetuado com sucesso. Um email de verificação foi enviado para validar seu cadastro.", HttpStatus.BAD_GATEWAY);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Erro no cadastro", HttpStatus.BAD_GATEWAY);
		}

	}

}
