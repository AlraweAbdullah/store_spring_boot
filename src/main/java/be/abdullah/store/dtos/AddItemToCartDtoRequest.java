package be.abdullah.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartDtoRequest {

    @NotNull(message = "{validate.cart.product.required}")
    private Long productId;
}