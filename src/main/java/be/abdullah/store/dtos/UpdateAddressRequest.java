package be.abdullah.store.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAddressRequest {

    @NotBlank(message = "{validate.address.street.required}")
    private String street;

    @NotBlank(message = "{validate.address.city.required}")
    private String city;

    @NotBlank(message = "{validate.address.state.required}")
    private String state;

    @NotBlank(message = "{validate.address.postcode.required}")
    private String postcode;
}