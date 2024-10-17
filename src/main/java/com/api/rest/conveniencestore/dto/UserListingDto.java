package com.api.rest.conveniencestore.dto;

import com.api.rest.conveniencestore.enums.Roles;
import com.api.rest.conveniencestore.enums.Status;
import com.api.rest.conveniencestore.model.User;

public record UserListingDto(
        Long id,

        String username,

        String email,

        Status status,

        Roles role) {

    public UserListingDto(User user) { //construtor
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus(),
                user.getRole()
        );
    }
}
//Com a classe UserListingDto, consigo fitrar os dados que quero exibir,
// protegendo dados sensiveis como senhas dos usuarios.
//Poupando tambem, tempo de processamento.