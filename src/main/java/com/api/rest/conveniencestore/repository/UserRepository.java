package com.api.rest.conveniencestore.repository;

import com.api.rest.conveniencestore.model.User;
import com.api.rest.conveniencestore.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

//Esta classe está herdando do JpaRepository o método FindAll, então os métodos do crud,
// ja existe declarado na interface, por meio do JPA.
public interface UserRepository extends JpaRepository <User, Long> { //repository acessa o banco de dados.
    //busca os usuarios com base no statos ativo ou inativo
    Collection<User> findByStatus(Status status);

    Optional<UserDetails> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsById(Long id);

    boolean existsByEmail(String email);
}