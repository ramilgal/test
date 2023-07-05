package ru.skypro.lessons.springboot.test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "auth_user")
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;
    @JoinColumn (name = "role_id")
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Roles role;


}
