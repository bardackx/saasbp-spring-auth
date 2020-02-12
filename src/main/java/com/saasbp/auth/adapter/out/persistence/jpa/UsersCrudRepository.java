package com.saasbp.auth.adapter.out.persistence.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersCrudRepository extends CrudRepository<UserEntity, UUID> {

	Optional<UserEntity> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<UserEntity> findByEmailConfirmationCode(UUID code);

	@Modifying
	@Query("update UserEntity u set u.isEmailConfirmed = ?2 where u.emailConfirmationCode = ?1")
	void updateEmailConfirmation(UUID code, boolean confirmed);

}
