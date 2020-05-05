package krzysztof_k_service_client_app.controller;

import krzysztof_k_service_client_app.common.dto.ClientDto;
import krzysztof_k_service_client_app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<ClientDto> getClients(){
        return this.clientService.findAllClients();
    }

    @RequestMapping(method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto addClient(@Valid @RequestBody ClientDto clientDto){
        return this.clientService.addClient(clientDto);
    }
}
