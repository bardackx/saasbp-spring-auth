package com.saasbp.auth.adapter.out.persistence.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface EmailResetsCrudRepository extends CrudRepository<EmailResetEntity, UUID> {

}
