package be.abdullah.store.mappers;

import be.abdullah.store.dtos.RegisterUserRequest;
import be.abdullah.store.dtos.UpdateUserRequest;
import be.abdullah.store.dtos.UserDto;
import be.abdullah.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest userDto);


    void update(UpdateUserRequest request, @MappingTarget User user);
}
