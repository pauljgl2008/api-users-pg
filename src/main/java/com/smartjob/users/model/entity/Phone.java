package com.smartjob.users.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

/**
 * Represents a phone number associated with a user.
 */
@Data
@Entity
@Table(name = "PHONE")
public class Phone {

    /**
     * Unique identifier for the phone.
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private UUID id;

    /**
     * The user associated with this phone number.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    /**
     * The phone number itself.
     */
    private String number;

    /**
     * The city code of the phone number.
     */
    private String cityCode;

    /**
     * The country code of the phone number.
     */
    private String countryCode;

}
