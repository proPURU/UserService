package com.example.userservice.controller;

import com.example.userservice.dtos.*;
import com.example.userservice.exception.UserAlreadyExistsException;
import com.example.userservice.exception.UserDoesNotExistException;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    public AuthController(AuthService authServices)
    {
        this.authService=authServices;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) throws UserDoesNotExistException
    {
        ResponseEntity<UserDto> userDtoResponseEntity=authService.login(request.getEmail(),request.getPassword());
        return userDtoResponseEntity;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request) throws UserAlreadyExistsException {
        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidatetokenResponseDto> validateToken(@RequestBody ValidateTokenRequestDto request) {
        Optional<UserDto> userDto = authService.validate(request.getToken(), request.getUserId());

        if (userDto.isEmpty()) {
            ValidatetokenResponseDto response = new ValidatetokenResponseDto();
            response.setSessionStatus(SessionStatus.INVALID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ValidatetokenResponseDto response = new ValidatetokenResponseDto();
        response.setSessionStatus(SessionStatus.ACTIVE);
        response.setUserDto(userDto.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
