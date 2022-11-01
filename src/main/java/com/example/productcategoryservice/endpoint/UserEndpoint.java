package com.example.productcategoryservice.endpoint;

import com.example.productcategoryservice.dto.UserLoginRequestDto;
import com.example.productcategoryservice.dto.UserRegisterDto;
import com.example.productcategoryservice.entity.User;
import com.example.productcategoryservice.mapper.UserMapper;
import com.example.productcategoryservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserEndpoint {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto userRegisterDto) {
        User user = userMapper.map(userRegisterDto);
        if (!userService.registerUser(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        userService.registerUser(user);
        return ResponseEntity.ok(userMapper.map(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto userLoginDto) {
        if (userService.login(userLoginDto) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.login(userLoginDto));
    }



}
