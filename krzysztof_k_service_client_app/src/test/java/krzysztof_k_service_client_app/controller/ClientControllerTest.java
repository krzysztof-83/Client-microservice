package krzysztof_k_service_client_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import krzysztof_k_service_client_app.service.ClientService;
import krzysztof_k_service_client_app.common.dto.ClientDto;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration
@WebAppConfiguration
public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    private MockMvc mockMvc;

    @InjectMocks
    private ClientController clientController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void shouldShowAllClients() throws Exception {

        // given
        ClientDto clientDto = createClient();
        Set<ClientDto> clientsDto = new HashSet<>();
        clientsDto.add(clientDto);
        Mockito.when(clientService.findAllClients()).thenReturn(clientsDto);

        // when
        ResultActions response = this.mockMvc.perform(get("/clients").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content("1"));

        // then
        response.andExpect(status().isOk())//
                .andExpect(jsonPath("[0].id").value(clientDto.getId().intValue()))
                .andExpect(jsonPath("[0].firstName").value(clientDto.getFirstName()))
                .andExpect(jsonPath("[0].surname").value(clientDto.getSurname()))
                .andExpect(jsonPath("[0].email").value(clientDto.getEmail()))
                .andExpect(jsonPath("[0].telephone").value(clientDto.getTelephone()))
                .andExpect(jsonPath("[0].creditCardNumber").value(clientDto.getCreditCardNumber()));

    }

    @Test
    public void shouldAddNewClient() throws Exception {

        // given
        ClientDto clientDto = createClient();
        Mockito.when(clientService.addClient(any(ClientDto.class))).thenReturn(clientDto);

        // when
        ResultActions response = this.mockMvc.perform(post("/clients").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(clientDto)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("surname").value("Kovalsky"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("kovalsky@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("telephone").value("888-888-888"))
                .andExpect(MockMvcResultMatchers.jsonPath("creditCardNumber").value("6391007802683739"));
        verify(clientService, times(1)).addClient(any(ClientDto.class));
    }

    @Test
    public void shouldNotAddNewClientWithNullEmail() throws Exception {

        // given
        ClientDto clientDto = createClient();
        clientDto.setEmail(null);
        Mockito.when(clientService.addClient(any(ClientDto.class))).thenReturn(clientDto);

        // when
        ResultActions response = this.mockMvc.perform(post("/clients").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(clientDto)));

        // then
        response.andExpect(status().is4xxClientError());
        Mockito.verifyZeroInteractions(clientService);
    }

    @Test
    public void shouldNotAddNewClientWithEmptyEmail() throws Exception {

        // given
        ClientDto clientDto = createClient();
        clientDto.setEmail("");
        Mockito.when(clientService.addClient(any(ClientDto.class))).thenReturn(clientDto);

        // when
        ResultActions response = this.mockMvc.perform(post("/clients").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(clientDto)));

        // then
        response.andExpect(status().is4xxClientError());
        Mockito.verifyZeroInteractions(clientService);
    }

    private ClientDto createClient() {
        ClientDto newClient = new ClientDto();
        newClient.setId(1L);
        newClient.setFirstName("John");
        newClient.setSurname("Kovalsky");
        newClient.setEmail("kovalsky@gmail.com");
        newClient.setTelephone("888-888-888");
        newClient.setCreditCardNumber("6391007802683739");
        return newClient;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}