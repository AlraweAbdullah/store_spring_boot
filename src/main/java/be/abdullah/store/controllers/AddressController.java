package be.abdullah.store.controllers;

import be.abdullah.store.dtos.AddressDto;
import be.abdullah.store.dtos.RegisterAddressRequest;
import be.abdullah.store.dtos.UpdateAddressRequest;
import be.abdullah.store.exceptions.AddressNotFoundException;
import be.abdullah.store.exceptions.UserNotFoundException;
import be.abdullah.store.services.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
@Tag(name = "Addresses")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/{userId}/addresses")
    public List<AddressDto> getAddresses(@PathVariable Long userId) {
        return addressService.getAddresses(userId);
    }

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<AddressDto> createAddress(@PathVariable Long userId, @RequestBody RegisterAddressRequest request, UriComponentsBuilder uriBuilder) {
        System.out.println(userId);
        var addressDto = addressService.createAddress(userId, request);
        var uri = uriBuilder.path("/api/v1/users/{userId}/addresses/{addressId}").buildAndExpand(userId, addressDto.getId()).toUri();

        return ResponseEntity.created(uri).body(addressDto);
    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public AddressDto updateAddress(@PathVariable Long userId, @PathVariable Long addressId, @RequestBody UpdateAddressRequest request) {
        return addressService.updateAddress(userId, addressId, request);
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(Long userId, Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAddressNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Address not found"));
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "User not found"));
    }

}
