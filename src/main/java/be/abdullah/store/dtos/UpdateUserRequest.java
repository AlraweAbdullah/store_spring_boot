package be.abdullah.store.dtos;

import be.abdullah.store.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "{validate.user.name.required}")
    @Size(
            min = 2,
            max = 255,
            message = "{validate.user.name.size}"
    )
    private String name;

    @NotBlank(message = "{validate.user.email.required}")
    @Email(message = "{validate.user.email.invalid}")
    @Size(
            max = 255,
            message = "{validate.user.email.size}"
    )
    @Lowercase(message = "{validate.user.email.lowercase}")
    private String email;
}