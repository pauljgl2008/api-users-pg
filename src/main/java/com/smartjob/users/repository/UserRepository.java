package com.smartjob.users.repository;

import com.smartjob.users.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository provides methods to interact with User entities
 * in the database.
 */
public interface UserRepository {

    List<User> findAll();

    Optional<User> findByEmail(String email);

    User save(final User user);

}

