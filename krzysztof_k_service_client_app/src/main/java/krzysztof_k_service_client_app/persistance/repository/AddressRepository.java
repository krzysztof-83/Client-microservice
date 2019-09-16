package krzysztof_k_service_client_app.persistance.repository;

import krzysztof_k_service_client_app.persistance.domain.AddressEntity;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
}
