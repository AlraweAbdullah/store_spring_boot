package be.abdullah.store.dtos;

import be.abdullah.store.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {

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

    @NotBlank(message = "{validate.user.password.required}")
    @Size(
            min = 6,
            max = 255,
            message = "{validate.user.password.size}"
    )
    private String password;
}