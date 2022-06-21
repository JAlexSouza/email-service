package br.com.usermanager.core.model.dto;

import java.time.LocalDate;

import br.com.usermanager.core.model.entity.UserKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserKeyDTO {
	
	private UserDTO userDTO;
	private String keySecrete;
	private LocalDate registrationDate;
	
	public static UserKeyDTO convert(UserKey userKey) {
		return UserKeyDTO.builder()
							.userDTO(UserDTO.convert(userKey.getUser()))
							.keySecrete(userKey.getKeySecrete())
							.registrationDate(userKey.getRegistrationDate())
							.build();
	}

}
