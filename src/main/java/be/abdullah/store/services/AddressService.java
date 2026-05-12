package be.abdullah.store.services;

import be.abdullah.store.dtos.AddressDto;
import be.abdullah.store.dtos.RegisterAddressRequest;
import be.abdullah.store.dtos.UpdateAddressRequest;
import be.abdullah.store.exceptions.AddressNotFoundException;
import be.abdullah.store.exceptions.UserNotFoundException;
import be.abdullah.store.mappers.AddressMapper;
import be.abdullah.store.repositories.AddressRepository;
import be.abdullah.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    public List<AddressDto> getAddresses(Long userId) {
        return addressRepository.findByUserId(userId).stream().map(addressMapper::toDto).toList();
    }

    public AddressDto createAddress(Long userId, RegisterAddressRequest request) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        var address = addressMapper.toEntity(request);
        address.setUser(user);
        var savedAddress = addressRepository.save(address);

        return addressMapper.toDto(savedAddress);
    }

    public AddressDto updateAddress(Long userId, Long addressId, UpdateAddressRequest request) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        var address = addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);

        addressMapper.update(request, address);
        var savedAddress = addressRepository.save(address);
        return addressMapper.toDto(savedAddress);
    }

    public void deleteAddress(Long userId, Long addressId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        var address = addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);
        addressRepository.delete(address);
    }
}
