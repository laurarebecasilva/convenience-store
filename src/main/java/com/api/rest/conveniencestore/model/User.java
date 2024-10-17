package com.api.rest.conveniencestore.model;

import com.api.rest.conveniencestore.dto.UserDto;
import com.api.rest.conveniencestore.dto.UserUpdateDto;
import com.api.rest.conveniencestore.enums.Roles;
import com.api.rest.conveniencestore.enums.Status;

import com.api.rest.conveniencestore.exceptions.UserNotValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")//gera um equalshashcode apenas no atributo id
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    //@NotBlank(message = "Username cannot be blank")
    //@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @JsonIgnore
    @Column(unique = true)
    @NotBlank(message = "Password cannot be blank") // ^înicio da string, lockhead (?=.*[0-9]) verifica se há pelo menos um digito em toda string
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$", message = "Password must be at least 8 characters long and include both letters and numbers")
    private String password; //.* qualquer caractere em qualquer posição. $ fim da string

    @JsonIgnore
    @Column(unique = true)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Roles role;

    public User(UserDto data) {
        this.username = data.username();
        this.password = data.password();
        this.email = data.email();
        this.status = Status.ACTIVE;
        this.role = Roles.USER;
    }

    public void updateData(UserUpdateDto userUpdateDto, PasswordEncoder passwordEncoder) throws UserNotValidPassword {
        if (userUpdateDto.username() != null) {
            this.username = userUpdateDto.username();
        }
        if (userUpdateDto.password() != null && !userUpdateDto.password().isBlank()) {
            validatePassword(userUpdateDto.password());  // Validação centralizada da senha
            String encryptedPassword = passwordEncoder.encode(userUpdateDto.password());
            this.password = encryptedPassword;  // Atualiza a senha criptografada.
        }
    }

    private void validatePassword(String password) throws UserNotValidPassword {
        if (password.length() < 8) {
            throw new UserNotValidPassword("A senha deve ter pelo menos 8 caracteres.");
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
            throw new UserNotValidPassword("A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula e um número.");
        }
    }

    public void setStatus(Status status) { //setter status
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(() -> "ROLE: " + this.role);
    }
}
