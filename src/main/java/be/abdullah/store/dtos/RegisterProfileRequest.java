package be.abdullah.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterProfileRequest {
    @NotNull(message = "User id is required")
    private Long userId;

    private String bio;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Integer loyaltyPoints;
}
