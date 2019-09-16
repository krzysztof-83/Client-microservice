package krzysztof_k_service_client_app.persistance.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private long version;
    private String zipCode;
    private String city;
    private String street;
    private Integer house;
    private String country;
}
