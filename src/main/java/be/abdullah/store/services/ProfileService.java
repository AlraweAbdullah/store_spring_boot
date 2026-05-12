package be.abdullah.store.services;

import be.abdullah.store.dtos.ProfileDto;
import be.abdullah.store.dtos.RegisterProfileRequest;
import be.abdullah.store.dtos.UpdateProfileRequest;
import be.abdullah.store.exceptions.ProfileAlreadyExistsException;
import be.abdullah.store.exceptions.ProfileNotFoundException;
import be.abdullah.store.exceptions.UserNotFoundException;
import be.abdullah.store.mappers.ProfileMapper;
import be.abdullah.store.repositories.ProfileRepository;
import be.abdullah.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class ProfileService {
    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileDto getProfile(Long profileId) {
        var profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);
        return profileMapper.toDto(profile);
    }


    public ProfileDto registerProfile(RegisterProfileRequest request) {
        var user = userRepository.findById(request.getUserId()).orElseThrow(UserNotFoundException::new);
        var exists = profileRepository.existsById(user.getId());
        if (exists) {
            throw new ProfileAlreadyExistsException();
        }
        var profile = profileMapper.toEntity(request);

        var savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    public ProfileDto updateProfile(Long profileId, UpdateProfileRequest request) {
        var profile = profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);

        profileMapper.update(request, profile);
        profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }
}
