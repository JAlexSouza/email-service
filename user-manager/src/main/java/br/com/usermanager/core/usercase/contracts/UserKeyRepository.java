package br.com.usermanager.core.usercase.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.usermanager.core.model.entity.UserKey;

@Repository
public interface UserKeyRepository extends JpaRepository<UserKey, Long> {

}
