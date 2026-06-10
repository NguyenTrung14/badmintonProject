package org.example.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.project.exception.HttpNotFoundException;
import org.example.project.exception.ValidAlreadyExistsException;
import org.example.project.model.entity.User;
import org.example.project.model.entity.dto.RegisterRequestDto;
import org.example.project.repository.UserRepository;
import org.example.project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl implements UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public User registerUser(RegisterRequestDto registerRequestDto) {
        String Password = passwordEncoder.encode(registerRequestDto.getPassword());
        registerRequestDto.setPassword(Password);
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new ValidAlreadyExistsException("Email Already Exists");
        }
        if (userRepository.existsByUsername(registerRequestDto.getUsername())) {
            throw new ValidAlreadyExistsException("Username Already Exists");
        }
        if (userRepository.existsByPhoneNumber(registerRequestDto.getPhoneNumber())) {
            throw new ValidAlreadyExistsException("PhoneNumber Already Exists");
        }
        User user = new User(registerRequestDto);
        return userRepository.save(user);
    }

    @Override
    public User RepairUser(Long id,RegisterRequestDto registerRequestDto) {
        User user = userRepository.findById(id).orElseThrow(()-> new HttpNotFoundException("User not found"));
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new ValidAlreadyExistsException("Email Already Exists");
        }
        if (userRepository.existsByUsername(registerRequestDto.getUsername())) {
            throw new ValidAlreadyExistsException("Username Already Exists");
        }
        if (userRepository.existsByPhoneNumber(registerRequestDto.getPhoneNumber())) {
            throw new ValidAlreadyExistsException("PhoneNumber Already Exists");
        }
        user.setPassword(registerRequestDto.getPassword());
        user.setPhoneNumber(registerRequestDto.getPhoneNumber());
        user.setEmail(registerRequestDto.getEmail());
        user.setUsername(registerRequestDto.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new HttpNotFoundException("User not found"));
        user.setIsEnabled(false);
        userRepository.save(user);
        return null;
    }

    @Override
    public Page<User> findAllBySearch(String search, Pageable pageable) {
        return userRepository.findAllBySearch(search, pageable);
    }
}
