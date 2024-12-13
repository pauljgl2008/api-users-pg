package com.smartjob.users.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Represents a user in the system.
 */
@Data
@Entity
@Table(name="T_USER")
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private UUID id;

    /**
     * User's full name.
     */
    private String name;

    /**
     * User's email address (used for login).
     */
    private String email;

    /**
     * Hashed password for the user (should not be stored in plain text).
     */
    private String password;

    /**
     * List of phone numbers associated with the user.
     * (One-to-Many relationship with cascade type ALL for persisting phones along with the user)
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Phone> phones;

    /**
     * Timestamp of the user's creation.
     */
    private Instant created;

    /**
     * Timestamp of the user's last modification.
     */
    private Instant modified;

    /**
     * Timestamp of the user's last login.
     */
    private Instant lastLogin;

    /**
     * User's authentication token (used for API access).
     */
    private String token;

    /**
     * Flag indicating whether the user is active or not.
     */
    private Boolean isActive;
}
