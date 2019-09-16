package krzysztof_k_service_client_app.service.impl;

import krzysztof_k_service_client_app.service.ClientService;
import krzysztof_k_service_client_app.common.dto.AddressDto;
import krzysztof_k_service_client_app.common.dto.ClientDto;
import krzysztof_k_service_client_app.common.exception.InvalidParamException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class ClientServiceImplTest {

    @Autowired
    private ClientService clientService;

    @Test
    public void shouldFindAllClients(){

        // when
        Set<ClientDto> listOfClients = clientService.findAllClients();

        // then
        assertEquals(10, listOfClients.size());
    }

    @Test
    public void shouldAddNewClientWithAddress(){

        // given
        ClientDto newClient = createClient();
        AddressDto newAddress = createAddress();
        newClient.getAddresses().add(newAddress);

        // when
        ClientDto addedClient = clientService.addClient(newClient);

        // then
        assertEquals(11, clientService.findAllClients().size());
        assertTrue(addedClient.getId() != null);
        assertEquals(newClient.getFirstName(), addedClient.getFirstName());
        assertEquals(newClient.getSurname(), addedClient.getSurname());
        assertEquals(newClient.getEmail(), addedClient.getEmail());
        assertEquals(newClient.getTelephone(), addedClient.getTelephone());
        assertEquals(newClient.getCreditCardNumber(), addedClient.getCreditCardNumber());
        assertFalse(addedClient.getAddresses().isEmpty());
    }

    @Test
    public void shouldAddClientWithoutAddress(){

        // given
        ClientDto newClient = createClient();

        // when
        ClientDto addedClient = clientService.addClient(newClient);

        // then
        assertEquals(11, clientService.findAllClients().size());
        assertTrue(addedClient.getId() != null);
        assertEquals(newClient.getFirstName(), addedClient.getFirstName());
        assertEquals(newClient.getSurname(), addedClient.getSurname());
        assertEquals(newClient.getEmail(), addedClient.getEmail());
        assertEquals(newClient.getTelephone(), addedClient.getTelephone());
        assertEquals(newClient.getCreditCardNumber(), addedClient.getCreditCardNumber());
        assertTrue(addedClient.getAddresses().isEmpty());
    }

    @Test
    public void shouldAddTwoSameClientsWithoutAddresses(){

        // given
        ClientDto newClient = createClient();
        ClientDto newClient2 = createClient();

        // when
        ClientDto addedClient = clientService.addClient(newClient);
        ClientDto addedClient2 = clientService.addClient(newClient2);

        // then
        assertEquals(12, clientService.findAllClients().size());
        assertTrue(addedClient.getId() != null);
        assertTrue(addedClient2.getId() != null);
        assertEquals(newClient2.getFirstName(), addedClient2.getFirstName());
        assertEquals(newClient2.getSurname(), addedClient2.getSurname());
        assertEquals(newClient2.getEmail(), addedClient2.getEmail());
        assertEquals(newClient2.getTelephone(), addedClient2.getTelephone());
        assertEquals(newClient2.getCreditCardNumber(), addedClient2.getCreditCardNumber());
        assertEquals(newClient.getFirstName(), addedClient.getFirstName());
        assertEquals(newClient.getSurname(), addedClient.getSurname());
        assertEquals(newClient.getEmail(), addedClient.getEmail());
        assertEquals(newClient.getTelephone(), addedClient.getTelephone());
        assertEquals(newClient.getCreditCardNumber(), addedClient.getCreditCardNumber());
    }

    @Test(expected = InvalidParamException.class)
    public void shouldThrowExceptionWhenAddingNullClient(){

        // given
        ClientDto newClient = null;

        // when
        clientService.addClient(newClient);

        // then
        assertEquals(10, clientService.findAllClients().size());
    }

    private ClientDto createClient(){
        ClientDto newClient = new ClientDto();
        newClient.setFirstName("John");
        newClient.setSurname("Kovalsky");
        newClient.setEmail("kovalsky@gmail.com");
        newClient.setTelephone("888-888-888");
        newClient.setCreditCardNumber("6391007802683739");
        return newClient;
    }

    private AddressDto createAddress(){
        AddressDto newAddress = new AddressDto();
        newAddress.setHouse(50);
        newAddress.setCity("Wroclaw");
        newAddress.setCountry("Poland");
        newAddress.setStreet("Polna");
        newAddress.setZipCode("44-208");
        return newAddress;
    }
}

