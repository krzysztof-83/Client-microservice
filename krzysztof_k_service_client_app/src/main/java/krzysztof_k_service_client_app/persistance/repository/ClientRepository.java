package krzysztof_k_service_client_app.persistance.repository;

import krzysztof_k_service_client_app.persistance.domain.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
}
