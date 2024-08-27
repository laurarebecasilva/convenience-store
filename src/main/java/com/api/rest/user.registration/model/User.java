package com.api.rest.user.registration.model;

import com.api.rest.user.registration.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")//gera um equalshashcode apenas no atributo id
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String password;

    @Column(unique = true)
    private String email;

    public User(UserDto data) {
        this.username = data.username();
        this.password = data.password();
        this.email = data.email();
    }
}
