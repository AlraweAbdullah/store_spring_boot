package be.abdullah.store.dtos;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileRequest {
    private String bio;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Integer loyaltyPoints;
}
