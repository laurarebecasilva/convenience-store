package com.api.rest.user.registration.model;

import com.api.rest.user.registration.dto.UserDto;
import com.api.rest.user.registration.dto.UserUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "\\d{8}")
    private String password;

    @Column(unique = true)
    private String email;

    public User(UserDto data) {
        this.username = data.username();
        this.password = data.password();
        this.email = data.email();
    }

    public void updateData(UserUpdateDto userUpdateDto) {
        if (userUpdateDto.username() != null) {
            this.username = userUpdateDto.username(); //se o retorno do put vier preenchido (nao nulo), username pode ser atualizado.
        }
        if (userUpdateDto.password() != null) {
            this.password = userUpdateDto.password();
        }
    }
}
