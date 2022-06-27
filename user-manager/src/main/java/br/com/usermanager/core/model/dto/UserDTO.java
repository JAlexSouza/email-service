package br.com.usermanager.core.model.dto;

import br.com.usermanager.core.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	
	private String email;
	private String password;
	private String name;
	private String lastName;
	
	public static UserDTO convert(User user) {
		return UserDTO.builder()
							.email(user.getEmail())
							.password(user.getPassword())
							.name(user.getName())
							.lastName(user.getLastName())
							.build();
	}
	
}
