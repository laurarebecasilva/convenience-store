package com.api.rest.conveniencestore.service;

import com.api.rest.conveniencestore.dto.ClientDto;
import com.api.rest.conveniencestore.model.Client;
import com.api.rest.conveniencestore.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    ClientDto clientDto;

    @Test
    public void testRegisterClient() {
        clientDto = new ClientDto("laura", "123.445.677-88");
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Client client = clientService.registerClient(clientDto);

        assertNotNull(client);
        assertEquals("laura", client.getName());
        assertEquals("123.445.677-88", client.getCpf());
        verify(clientRepository, times(1)).save(any(Client.class));
    }
}
