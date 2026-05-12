package be.abdullah.store.controlers;

import be.abdullah.store.dtos.ChangePasswordRequest;
import be.abdullah.store.dtos.RegisterUserRequest;
import be.abdullah.store.dtos.UpdateUserRequest;
import be.abdullah.store.dtos.UserDto;
import be.abdullah.store.exceptions.DuplicatedEmailException;
import be.abdullah.store.exceptions.UserNotFoundException;
import be.abdullah.store.exceptions.WrongOldPasswordException;
import be.abdullah.store.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get all users")
    @GetMapping
    public List<UserDto> getUsers(@RequestParam(defaultValue = "name", required = false) String sort) {
        return userService.getUsers(sort);
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{userId}")
    public UserDto getUser(@Parameter(description = "User id") @PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @Operation(summary = "Register a user")
    @PostMapping
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder uriBuilder) {
        var userDto = userService.registerUser(request);
        var uri = uriBuilder.path("/api/v1/users/{userId}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @Operation(summary = "Update user by id")
    @PutMapping("/{userId}")
    public UserDto updateUser(@Parameter(description = "User id") @PathVariable Long userId, @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUser(userId, request);
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "User id") @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Change user password")
    @PostMapping("/{userId}/change-password")
    public ResponseEntity<Void> changePassword(@Parameter(description = "User id") @PathVariable Long userId, @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(userId, request);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<Map<String, String>> handleDuplicationEmailException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Email already registered"));
    }

    @ExceptionHandler(WrongOldPasswordException.class)
    public ResponseEntity<Map<String, String>> handleWrongPasswordException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Old password is not correct"));
    }
}
