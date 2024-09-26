package com.api.rest.conveniencestore.model;

import com.api.rest.conveniencestore.dto.UserDto;
import com.api.rest.conveniencestore.dto.UserUpdateDto;
import com.api.rest.conveniencestore.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Column // Remover unique, se não for necessário
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$", message = "Password must be at least 8 characters long and include both letters and numbers")
    private String password;

    @Column(unique = true)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    public User(UserDto data) {
        this.username = data.username();
        this.password = data.password();
        this.email = data.email();
        this.status = Status.ACTIVE;
    }

    //atualiza os valores dos campos apos validar se o campo esta nulo
    public void updateData(UserUpdateDto userUpdateDto) {
        if (userUpdateDto.username() != null) {
            this.username = userUpdateDto.username();  //se o retorno do put vier preenchido (nao nulo), username pode ser atualizado.
        }
        if (userUpdateDto.password() != null) {
            this.password = userUpdateDto.password();
        }
    }

    public void setStatus(Status status) { //setter status
        this.status = status;
    }
}
