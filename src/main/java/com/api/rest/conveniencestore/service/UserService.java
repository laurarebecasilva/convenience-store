package com.api.rest.conveniencestore.service;

import com.api.rest.conveniencestore.dto.UserDto;
import com.api.rest.conveniencestore.dto.UserListingDto;
import com.api.rest.conveniencestore.dto.UserUpdateDto;
import com.api.rest.conveniencestore.enums.Status;
import com.api.rest.conveniencestore.model.User;
import com.api.rest.conveniencestore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service //Serviço spring - gestão durante a execução.
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User registerUser(UserDto userDto) { // Registra novo usuário e salva no banco
        User user = new User(userDto);
        return userRepository.save(user);
    }

    //lista usuarios ativos
    public List<UserListingDto> listUsers() {
        return userRepository.findByStatus(Status.ACTIVE)
                .stream()
                .map(UserListingDto::new)
                .collect(Collectors.toList()); //retorna a lista de usuários que foi convertida por meio do stream, agora lista os dados sem comprometer os dados sensiveis.
    }

    @Transactional
    public User updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.getReferenceById(id);
        user.updateData(userUpdateDto);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.getReferenceById(id);
        userRepository.delete(user);
    }

    //atraves deste metodo, eu consigo inativar o usuario ativo, sem precisar exclui-lo.
    @Transactional
    public void statusUserInactive(Long id, Status status) {
        User user = userRepository.getReferenceById(id);
        user.setStatus(status.INACTIVE);
        userRepository.save(user);
    }
}
