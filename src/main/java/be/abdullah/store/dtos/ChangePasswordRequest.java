package be.abdullah.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {

    @NotBlank(message = "{validate.password.old.required}")
    private String oldPassword;

    @NotBlank(message = "{validate.password.new.required}")
    @Size(
            min = 6,
            max = 255,
            message = "{validate.password.new.size}"
    )
    private String newPassword;
}