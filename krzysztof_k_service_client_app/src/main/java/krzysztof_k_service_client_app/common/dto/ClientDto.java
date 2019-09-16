package krzysztof_k_service_client_app.common.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class ClientDto {

    private Long id;

    @NotBlank(message = "First name may not be blank")
    private String firstName;

    @NotBlank(message = "Surname may not be blank")
    private String surname;

    @NotBlank(message = "Email may not be blank")
    private String email;

    private String telephone;
    private String creditCardNumber;

    @NotNull(message = "Addresses may not be null")
    private Set<AddressDto> addresses = new HashSet<>();
}
