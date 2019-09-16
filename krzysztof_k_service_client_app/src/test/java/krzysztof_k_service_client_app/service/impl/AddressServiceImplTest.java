package krzysztof_k_service_client_app.service.impl;

import krzysztof_k_service_client_app.common.dto.AddressDto;
import krzysztof_k_service_client_app.common.exception.ClientNotFoundException;
import krzysztof_k_service_client_app.persistance.repository.ClientRepository;
import krzysztof_k_service_client_app.service.AddressService;
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
public class AddressServiceImplTest {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void shouldFindAllAddresses(){

        // when
        Set<AddressDto> addresses = addressService.findAllAddresses();

        // then
        assertEquals(1, addresses.size());
    }

    @Test
    public void shouldAddNewAddress(){

        // given
        AddressDto newAddress = createAddress();
        Long clientId = 1L;

        // when
        AddressDto addedAddress = addressService.addAddress(newAddress, clientId);

        // then
        assertEquals(2, addressService.findAllAddresses().size());
        assertTrue(addedAddress.getId() != null);
        assertEquals(newAddress.getCity(), addedAddress.getCity());
        assertEquals(newAddress.getCountry(), addedAddress.getCountry());
        assertEquals(newAddress.getHouse(), addedAddress.getHouse());
        assertEquals(newAddress.getStreet(), addedAddress.getStreet());
        assertEquals(newAddress.getZipCode(), addedAddress.getZipCode());
        assertEquals(2, clientRepository.findById(clientId).get().getAddresses().size());
    }

    @Test
    public void shouldAddTwoSameAddresses(){

        // given
        AddressDto newAddress = createAddress();
        AddressDto newAddress2 = createAddress();
        Long clientId = 1L;

        // when
        AddressDto addedAddress = addressService.addAddress(newAddress, clientId);
        AddressDto addedAddress2 = addressService.addAddress(newAddress2,clientId);

        // then
        assertEquals(3, addressService.findAllAddresses().size());
        assertTrue(addedAddress.getId() != null);
        assertTrue(addedAddress2.getId() != null);
        assertEquals(newAddress2.getCity(), addedAddress2.getCity());
        assertEquals(newAddress2.getCountry(), addedAddress2.getCountry());
        assertEquals(newAddress2.getHouse(), addedAddress2.getHouse());
        assertEquals(newAddress2.getStreet(), addedAddress2.getStreet());
        assertEquals(newAddress2.getZipCode(), addedAddress2.getZipCode());
        assertEquals(newAddress.getCity(), addedAddress.getCity());
        assertEquals(newAddress.getCountry(), addedAddress.getCountry());
        assertEquals(newAddress.getHouse(), addedAddress.getHouse());
        assertEquals(newAddress.getStreet(), addedAddress.getStreet());
        assertEquals(newAddress.getZipCode(), addedAddress.getZipCode());
        assertEquals(3, clientRepository.findById(clientId).get().getAddresses().size());
    }

    @Test(expected = InvalidParamException.class)
    public void shouldThrowExceptionWhenAddingNullAddress(){

        //  given
        AddressDto addressDto = null;
        Long clientId = 2L;

        // when
        addressService.addAddress(addressDto, clientId);

        // then
        assertEquals(1, addressService.findAllAddresses().size());
        assertTrue(clientRepository.findById(clientId).get().getAddresses().isEmpty());
    }

    @Test(expected = InvalidParamException.class)
    public void shouldThrowExceptionWhenAddingAddressWithoutClient(){

        //  given
        AddressDto addressDto = createAddress();
        Long clientId = null;

        // when
        addressService.addAddress(addressDto, clientId);

        // then
        assertEquals(1, addressService.findAllAddresses().size());
    }

    @Test(expected = ClientNotFoundException.class)
    public void shouldThrowExceptionWhenClientNotFound(){

        //  given
        AddressDto addressDto = createAddress();
        Long clientId = 20L;

        // when
        addressService.addAddress(addressDto, clientId);

        // then
        assertEquals(1, addressService.findAllAddresses().size());
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