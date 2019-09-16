package krzysztof_k_service_client_app.service.impl;

import krzysztof_k_service_client_app.persistance.domain.ClientEntity;
import krzysztof_k_service_client_app.common.dto.ClientDto;
import krzysztof_k_service_client_app.common.exception.InvalidParamException;
import krzysztof_k_service_client_app.persistance.repository.ClientRepository;
import krzysztof_k_service_client_app.service.ClientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Set;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ClientDto> findAllClients() {

        Iterable<ClientEntity> clients = clientRepository.findAll();
        Type type = new TypeToken<Set<ClientDto>>(){}.getType();
        return modelMapper.map(clients, type);
    }

    @Override
    public ClientDto addClient(ClientDto clientDto) {
        if(clientDto == null){
            throw new InvalidParamException("Client can't be null");
        }
        ClientEntity addedClient = clientRepository.save(modelMapper.map(clientDto, ClientEntity.class));
        return modelMapper.map(addedClient, ClientDto.class);
    }

}
