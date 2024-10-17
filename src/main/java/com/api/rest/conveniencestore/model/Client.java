package com.api.rest.conveniencestore.model;

import com.api.rest.conveniencestore.dto.ClientDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Table(name = "Clients")
@Entity(name = "Client")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")//gera um equalshashcode apenas no atributo id
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @Column(unique = true) // CPF único e obrigatório
    private String cpf;

    public Client(ClientDto data) {
        this.cpf = data.cpf();
        this.name = data.name();
    }

    public void setCpf(Client client) { //setter
        this.cpf = cpf;
    }

    public void setName(Client client) {
        this.name = name;
    }

}
