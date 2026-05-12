package be.abdullah.store.mappers;

import be.abdullah.store.dtos.AddressDto;
import be.abdullah.store.dtos.RegisterAddressRequest;
import be.abdullah.store.dtos.UpdateAddressRequest;
import be.abdullah.store.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "userId", source = "user.id")
    AddressDto toDto(Address address);

    Address toEntity(RegisterAddressRequest request);

    void update(UpdateAddressRequest request, @MappingTarget Address address);

}
