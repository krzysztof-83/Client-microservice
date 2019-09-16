package krzysztof_k_service_client_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import krzysztof_k_service_client_app.common.dto.AddressDto;
import krzysztof_k_service_client_app.service.AddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration
@WebAppConfiguration
public class AddressControllerTest {

    @Mock
    private AddressService addressService;

    private MockMvc mockMvc;

    @InjectMocks
    private AddressController addressController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    public void shouldShowAllAddresses() throws Exception {

        // given
        AddressDto addressDto = createAddress();
        Set<AddressDto> addressesDto = new HashSet<>();
        addressesDto.add(addressDto);
        Mockito.when(addressService.findAllAddresses()).thenReturn(addressesDto);

        // when
        ResultActions response = this.mockMvc.perform(get("/addresses").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content("1"));

        // then
        response.andExpect(status().isOk())//
                .andExpect(jsonPath("[0].id").value(addressDto.getId().intValue()))
                .andExpect(jsonPath("[0].zipCode").value(addressDto.getZipCode()))
                .andExpect(jsonPath("[0].city").value(addressDto.getCity()))
                .andExpect(jsonPath("[0].street").value(addressDto.getStreet()))
                .andExpect(jsonPath("[0].house").value(addressDto.getHouse()))
                .andExpect(jsonPath("[0].country").value(addressDto.getCountry()));

    }

    @Test
    public void shouldAddNewAddress() throws Exception {

        // given
        AddressDto addressDto = createAddress();
        Mockito.when(addressService.addAddress(any(AddressDto.class), any(Long.class))).thenReturn(addressDto);

        // when
        ResultActions response = this.mockMvc.perform(post("/addresses").param("clientId", "1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(addressDto)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("zipCode").value("44-208"))
                .andExpect(MockMvcResultMatchers.jsonPath("city").value("Wroclaw"))
                .andExpect(MockMvcResultMatchers.jsonPath("street").value("Polna"))
                .andExpect(MockMvcResultMatchers.jsonPath("house").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("country").value("Poland"));
        verify(addressService, times(1)).addAddress(any(AddressDto.class), any(Long.class));
    }

    @Test
    public void shouldNotAddNewAddressWithoutStreet() throws Exception {

        // given
        AddressDto addressDto = createAddress();
        addressDto.setStreet(null);
        Mockito.when(addressService.addAddress(any(AddressDto.class), any(Long.class))).thenReturn(addressDto);

        // when
        ResultActions response = this.mockMvc.perform(post("/addresses").param("clientId", "1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(addressDto)));

        // then
        response.andExpect(status().is4xxClientError());
        Mockito.verifyZeroInteractions(addressService);
    }

    @Test
    public void shouldNotAddNewAddressWithEmptyStreet() throws Exception {

        // given
        AddressDto addressDto = createAddress();
        addressDto.setStreet("");
        Mockito.when(addressService.addAddress(any(AddressDto.class), any(Long.class))).thenReturn(addressDto);

        // when
        ResultActions response = this.mockMvc.perform(post("/addresses").param("clientId", "1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(addressDto)));

        // then
        response.andExpect(status().is4xxClientError());
        Mockito.verifyZeroInteractions(addressService);
    }

    private AddressDto createAddress() {
        AddressDto newAddress = new AddressDto();
        newAddress.setId(1L);
        newAddress.setHouse(50);
        newAddress.setCity("Wroclaw");
        newAddress.setCountry("Poland");
        newAddress.setStreet("Polna");
        newAddress.setZipCode("44-208");
        return newAddress;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}