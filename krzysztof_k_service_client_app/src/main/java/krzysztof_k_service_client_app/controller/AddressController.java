package krzysztof_k_service_client_app.controller;

import krzysztof_k_service_client_app.service.AddressService;
import krzysztof_k_service_client_app.common.dto.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@ResponseBody
@RequestMapping("/addresses")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<AddressDto> getAddresses(){
        return this.addressService.findAllAddresses();
    }

    @RequestMapping(method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto addAddress(@Valid @RequestBody AddressDto addressDto, @RequestParam("clientId") Long clientId){
        return this.addressService.addAddress(addressDto, clientId);
    }
}
