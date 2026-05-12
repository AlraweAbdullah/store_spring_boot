package be.abdullah.store.mappers;

import be.abdullah.store.dtos.ProfileDto;
import be.abdullah.store.dtos.RegisterProfileRequest;
import be.abdullah.store.dtos.UpdateProfileRequest;
import be.abdullah.store.entities.Profile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto toDto(Profile profile);

    Profile toEntity(RegisterProfileRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void update(UpdateProfileRequest request, @MappingTarget Profile profile);

}
