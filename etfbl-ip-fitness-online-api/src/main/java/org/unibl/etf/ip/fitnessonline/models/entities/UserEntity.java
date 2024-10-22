package org.unibl.etf.ip.fitnessonline.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "avatar_url", nullable = true, length = 500)
    private String avatarUrl;

    @Column(name = "activation_token", nullable = true, length = 4)
    private String activationToken;

}
