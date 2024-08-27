package com.api.rest.user.registration.repository;

import com.api.rest.user.registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> { //repository acessa o banco de dados.
    //Esta classe está herdando do JpaRepository o método FindAll, então os métodos do crud,
    // ja existe declarado na interface, por meio do JPA.
}
