package com.smartjob.users.repository;

import com.smartjob.users.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository provides methods to interact with User entities
 * in the database.
 */
@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) this.userJpaRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userJpaRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return this.userJpaRepository.save(user);
    }

}

