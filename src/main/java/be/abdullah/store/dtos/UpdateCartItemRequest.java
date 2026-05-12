package be.abdullah.store.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {

    @NotNull(message = "{validate.cart.quantity.required}")
    @Min(value = 1, message = "{validate.cart.quantity.min}")
    private Integer quantity;
}