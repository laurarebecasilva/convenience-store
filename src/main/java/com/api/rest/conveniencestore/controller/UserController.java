package com.api.rest.conveniencestore.controller;

import com.api.rest.conveniencestore.dto.UserDto;
import com.api.rest.conveniencestore.dto.UserListingDto;
import com.api.rest.conveniencestore.enums.Roles;
import com.api.rest.conveniencestore.model.User;
import com.api.rest.conveniencestore.service.UserService;
import com.api.rest.conveniencestore.dto.UserUpdateDto;
import com.api.rest.conveniencestore.enums.Status;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

    //Controlador responsável por gerenciar as requisições HTTP
    @RestController //torna uma class normal em classe Spring
    @RequestMapping("users") //possibilita fazer envio e recebimento de dados
    public class UserController {

    @Autowired //injeção de dependencias; O Spring instancia o atributo atraves dessa anotation.
    private UserService userService;

    @PostMapping // requisição
    @Transactional // transação ativa com o banco de dados: registra usuários
    public ResponseEntity<User> register(@Valid @RequestBody UserDto userDto) { //O spring se integra com o valid para aplicar as validações dos campos
        User savedUser = userService.registerUser(userDto);
        return ResponseEntity.status(201).body(savedUser);
    }

    @GetMapping // leitura usuarios cadastrados
    public ResponseEntity<List<UserListingDto>> list() {
        var returnList = userService.listUsers();
        return ResponseEntity.ok(returnList);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<User> update(@Valid @PathVariable Long id, @RequestBody UserUpdateDto updateDto){
        User updateUser = userService.updateUser(id, updateDto);
        return ResponseEntity.status(200).body(updateUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Transactional
    public ResponseEntity<User> status(@Valid @PathVariable Long id, @RequestBody Map<String, String> statusRequest) {
        String statusString = statusRequest.get("status");
        Status statusInactive = Status.fromValueStatus(statusString); // Converte a string para enum
        User updatedStatusUser = userService.statusUserInactive(id, statusInactive);
        return ResponseEntity.ok(updatedStatusUser);
    }

    @PatchMapping("/{id}/roles")
    @Transactional
    public ResponseEntity<User> roles(@Valid @PathVariable Long id, @RequestBody Map<String, String> rolesRequest) {
        String rolesString = rolesRequest.get("roles");
        Roles rolesAdmin = Roles.fromValueRoles(rolesString); // Converte a string para enum
        User updatedRoleAdmin = userService.roleUserAdmin(id, rolesAdmin);
        return ResponseEntity.ok(updatedRoleAdmin);
    }
}
