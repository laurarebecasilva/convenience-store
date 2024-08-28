package com.api.rest.user.registration.controller;

import com.api.rest.user.registration.dto.UserDto;
import com.api.rest.user.registration.dto.UserListingDto;
import com.api.rest.user.registration.dto.UserUpdateDto;
import com.api.rest.user.registration.service.UserService;
import com.api.rest.user.registration.model.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Controlador responsável por gerenciar as requisições HTTP
@RestController //torna uma class normal em classe Spring
@RequestMapping("users") //possibilita fazer envio e recebimento de dados
public class UserController {

    @Autowired //injeção de dependencias; O Spring instancia o atributo atraves dessa anotation.
    private UserService userService;

    @PostMapping // requisição
    @Transactional // transação ativa com o banco de dados: registra usuários
    public ResponseEntity<User> register(@Valid @RequestBody UserDto data) { //O spring se integra com o valid para aplicar as validações dos campos
        User dataSaved = userService.registerUser(data);
        return ResponseEntity.status(200).body(dataSaved);
    }

    @GetMapping // leitura usuarios cadastrados
    public List<UserListingDto> list() {
        return userService.listUsers();
    }

    @PutMapping("/{id}")
    @Transactional
    public void update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto updateDto){
       User updateDataSaved = userService.updateUser(id, updateDto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
