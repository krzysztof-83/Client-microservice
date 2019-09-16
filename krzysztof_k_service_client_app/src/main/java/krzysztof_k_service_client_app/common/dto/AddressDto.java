package krzysztof_k_service_client_app.common.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class AddressDto {

    private Long id;

    @NotBlank(message = "Zip code may not be blank")
    private String zipCode;

    @NotBlank(message = "City may not be blank")
    private String city;

    @NotBlank(message = "Street may not be blank")
    private String street;

    @NotNull(message = "House may not be null")
    private Integer house;

    private String country;
}
