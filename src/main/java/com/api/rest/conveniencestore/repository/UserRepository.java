package com.api.rest.conveniencestore.repository;

import com.api.rest.conveniencestore.model.User;
import com.api.rest.conveniencestore.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
//Esta classe está herdando do JpaRepository o método FindAll, então os métodos do crud,
// ja existe declarado na interface, por meio do JPA.
public interface UserRepository extends JpaRepository <User, Long> { //repository acessa o banco de dados.

    //busca os usuarios com base no statos ativo ou inativo
    Collection<User> findByStatus(Status status);
}
