package com.saasbp.auth.adapter.out.persistence.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface PasswordResetsCrudRepository extends CrudRepository<PasswordResetEntity, UUID> {

}
