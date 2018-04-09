package com.epam.internship.carrental.alert.user;

import lombok.*;

import javax.persistence.*;

/**
 * This is an entity class for storing Users.
 */
@Entity
public @Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
class User {

    /**
     * The id is used for identifying the records in the database.
     * <p>
     * It's value is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  The userEmail field stores the users email address.
     */
    @Column(nullable = false)
    private String userEmail;
}
