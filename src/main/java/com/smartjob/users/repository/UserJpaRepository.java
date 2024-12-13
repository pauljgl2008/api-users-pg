package com.smartjob.users.repository;

import com.smartjob.users.model.entity.User;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends CrudRepository<User, UUID> {

    Optional<User> findByEmail(String email);

}

