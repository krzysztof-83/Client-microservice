package krzysztof_k_service_client_app.service;

import krzysztof_k_service_client_app.common.dto.AddressDto;

import java.util.Set;

/**
 * Service for operations add new address and to get all addresses
 */
public interface AddressService {

    /**
     * Finds all addresses
     * @return Set of addresses
     */
    Set<AddressDto> findAllAddresses();

    /**
     * Adds a new address for given client's id
     * @param addressDto - new address which will to be added
     * @param clientId - client id to who's address will be added
     * @return Address which was created
     */
    AddressDto addAddress(AddressDto addressDto, Long clientId);
}
