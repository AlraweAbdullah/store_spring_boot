package be.abdullah.store.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    @NotBlank(message = "{validate.product.name.required}")
    @Size(
            min = 2,
            max = 255,
            message = "{validate.product.name.size}"
    )
    private String name;

    @NotNull(message = "{validate.product.price.required}")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "{validate.product.price.min}"
    )
    private BigDecimal price;

    @NotNull(message = "{validate.product.description.required}")
    @Size(
            max = 255,
            message = "{validate.product.description.size}"
    )
    private String description;

    @NotNull(message = "{validate.product.category.required}")
    private Byte categoryId;
}