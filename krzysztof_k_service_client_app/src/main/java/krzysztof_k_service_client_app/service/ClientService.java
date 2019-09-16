package krzysztof_k_service_client_app.service;

import krzysztof_k_service_client_app.common.dto.ClientDto;

import java.util.Set;

/**
 * Service for operations add new client and to get all clients and their addresses
 */
public interface ClientService {

    /**
     * Finds all clients
     * @return Set of clients
     */
    Set<ClientDto> findAllClients();

    /**
     * Adds a new client
     * @param clientDto - new client who will be addded
     * @return client who was created
     */
    ClientDto addClient(ClientDto clientDto);
}
