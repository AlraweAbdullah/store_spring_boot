package be.abdullah.store.services;

import be.abdullah.store.dtos.ChangePasswordRequest;
import be.abdullah.store.dtos.RegisterUserRequest;
import be.abdullah.store.dtos.UpdateUserRequest;
import be.abdullah.store.dtos.UserDto;
import be.abdullah.store.exceptions.DuplicatedEmailException;
import be.abdullah.store.exceptions.UserNotFoundException;
import be.abdullah.store.exceptions.WrongOldPasswordException;
import be.abdullah.store.mappers.UserMapper;
import be.abdullah.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getUsers(String sort) {
        sort = Set.of("name", "email").contains(sort) ? sort : "name";
        return userRepository.findAll(Sort.by(sort)).stream().map(userMapper::toDto).toList();
    }

    public UserDto getUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }

    public UserDto registerUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicatedEmailException();
        }

        var user = userRepository.save(userMapper.toEntity(request));

        return userMapper.toDto(user);
    }

    public UserDto updateUser(Long userId, UpdateUserRequest request) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (userRepository.existsByEmailAndIdNot(request.getEmail(), user.getId())) {
            throw new DuplicatedEmailException();
        }

        userMapper.update(request, user);

        var savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public void deleteUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    public void changePassword(Long userId,  ChangePasswordRequest request) {
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new WrongOldPasswordException();
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }


}
