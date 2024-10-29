package com.api.rest.conveniencestore.service;

import com.api.rest.conveniencestore.dto.UserDto;
import com.api.rest.conveniencestore.dto.UserUpdateDto;
import com.api.rest.conveniencestore.enums.Roles;
import com.api.rest.conveniencestore.enums.Status;
import com.api.rest.conveniencestore.exceptions.PasswordValidateException;
import com.api.rest.conveniencestore.exceptions.UserNotFoundException;
import com.api.rest.conveniencestore.exceptions.UsernameValidateException;
import com.api.rest.conveniencestore.model.User;
import com.api.rest.conveniencestore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    UserDto userDto;

    @Test
    public void testRegisterUser() { //test register user
        userDto = new UserDto("alana", "Lauraconsegue34", "alana@gmail.com", Roles.ADMIN, Status.ACTIVE);

        String encodedPassword = "encodedPassword"; // Simula a senha codificada
        when(passwordEncoder.encode(userDto.password())).thenReturn(encodedPassword);

        // Mock do comportamento de salvamento
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ação: Chama o método registerUser
        User user = userService.registerUser(userDto);

        // Verificação: Confirma os resultados
        assertNotNull(user);
        assertEquals("alana", user.getUsername());
        assertEquals("alana@gmail.com", user.getEmail());
        assertEquals(encodedPassword, user.getPassword()); // Verifica se a senha foi codificada
        verify(userRepository, times(1)).save(any(User.class)); // Verifica se save foi chamado uma vez
    }


    @Test
    void testRegisterUserPassword() {
        UserDto userDto = new UserDto("alana", "Lauraconsegue34", "alana@gmail.com", Roles.ADMIN, Status.ACTIVE);
        User user = new User(userDto);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(userDto);

        assertNotNull(registeredUser);
        assertEquals("Lauraconsegue34", registeredUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }



    @Test
    void testUpdateUser() throws UserNotFoundException, PasswordValidateException, UsernameValidateException {
        Long userId = 1L;
        UserUpdateDto userUpdateDto = new UserUpdateDto("lala", "Ksbjfn23", "lala@gmail.com");
        User user = new User(userUpdateDto);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(userId, userUpdateDto);

        assertNotNull(updatedUser);
        assertEquals(userUpdateDto.username(), updatedUser.getUsername());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void testDeleteUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.getReferenceById(userId)).thenReturn(user);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testStatusUserInactive() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setStatus(Status.ACTIVE);

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User inactiveUser = userService.statusUserInactive(userId, Status.INACTIVE);

        assertEquals(Status.INACTIVE, inactiveUser.getStatus());
        verify(userRepository, times(1)).getReferenceById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRoleUserAdmin() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setRole(Roles.USER);

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        User adminUser = userService.roleUserAdmin(userId, Roles.ADMIN);

        assertEquals(Roles.ADMIN, adminUser.getRole());
        verify(userRepository, times(1)).getReferenceById(userId);
        verify(userRepository, times(1)).save(user);
    }

}
