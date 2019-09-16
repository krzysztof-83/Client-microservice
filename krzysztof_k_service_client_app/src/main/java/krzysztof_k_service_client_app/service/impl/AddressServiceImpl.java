package krzysztof_k_service_client_app.service.impl;


import krzysztof_k_service_client_app.persistance.domain.AddressEntity;
import krzysztof_k_service_client_app.persistance.domain.ClientEntity;
import krzysztof_k_service_client_app.common.dto.AddressDto;
import krzysztof_k_service_client_app.common.exception.ClientNotFoundException;
import krzysztof_k_service_client_app.common.exception.InvalidParamException;
import krzysztof_k_service_client_app.persistance.repository.AddressRepository;
import krzysztof_k_service_client_app.persistance.repository.ClientRepository;
import krzysztof_k_service_client_app.service.AddressService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    private ClientRepository clientRepository;

    private ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ClientRepository clientRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<AddressDto> findAllAddresses() {
        Iterable<AddressEntity> addresses = addressRepository.findAll();
        Type type = new TypeToken<Set<AddressDto>>() {
        }.getType();
        return modelMapper.map(addresses, type);
    }

    @Override
    public AddressDto addAddress(AddressDto addressDto, Long clientId) {
        if (addressDto == null || clientId == null) {
            throw new InvalidParamException("Parameters can't be null!");
        }
        Optional<ClientEntity> foundOptional = clientRepository.findById(clientId);
        if (!foundOptional.isPresent()) {
            throw new ClientNotFoundException("Client not found!");
        }
        ClientEntity foundClient = foundOptional.get();
        AddressEntity addedAddress = addressRepository.save(modelMapper.map(addressDto, AddressEntity.class));
        foundClient.getAddresses().add(addedAddress);
        clientRepository.save(foundClient);
        return modelMapper.map(addedAddress, AddressDto.class);
    }
}
