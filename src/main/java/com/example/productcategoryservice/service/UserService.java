package com.example.productcategoryservice.service;

import com.example.productcategoryservice.dto.UserLoginRequestDto;
import com.example.productcategoryservice.dto.UserLoginResponseDto;
import com.example.productcategoryservice.dto.UserRegisterDto;
import com.example.productcategoryservice.entity.Role;
import com.example.productcategoryservice.entity.User;
import com.example.productcategoryservice.mapper.UserMapper;
import com.example.productcategoryservice.repository.UserRepository;
import com.example.productcategoryservice.utility.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserMapper userMapper;

    public boolean registerUser(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return true;
    }

    public UserLoginResponseDto login(UserLoginRequestDto userLoginDto) {
        Optional<User> byEmail = userRepository.findByEmail(userLoginDto.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
                return UserLoginResponseDto.builder()
                        .token(jwtTokenUtil.generateToken(user.getEmail()))
                        .userAuthDto(userMapper.map(user))
                        .build();
            }
        }
        return null;
    }
}
