package be.abdullah.store.controllers;

import be.abdullah.store.dtos.ProfileDto;
import be.abdullah.store.dtos.RegisterProfileRequest;
import be.abdullah.store.dtos.UpdateProfileRequest;
import be.abdullah.store.exceptions.ProfileAlreadyExistsException;
import be.abdullah.store.exceptions.ProfileNotFoundException;
import be.abdullah.store.exceptions.UserNotFoundException;
import be.abdullah.store.services.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "Profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{profileId}")
    public ProfileDto getProfile(@PathVariable Long profileId) {
        return profileService.getProfile(profileId);
    }


    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(@Valid @RequestBody RegisterProfileRequest request, UriComponentsBuilder uriBuilder) {
        var profileDto = profileService.registerProfile(request);
        var uri = uriBuilder.path("/api/v1/profiles/{profileId}").buildAndExpand(profileDto.getId()).toUri();

        return ResponseEntity.created(uri).body(profileDto);
    }

    @PatchMapping("/{profileId}")
    public ProfileDto updateProfile(@PathVariable Long profileId, @RequestBody UpdateProfileRequest request) {
       return profileService.updateProfile(profileId, request);
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProfileNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Profile not found"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    }

    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleProfileAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "User already has a profile"));
    }

}
