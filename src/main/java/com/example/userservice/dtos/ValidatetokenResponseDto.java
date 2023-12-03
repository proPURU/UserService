package com.example.userservice.dtos;

import com.example.userservice.models.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatetokenResponseDto {
    private UserDto userDto;
    private SessionStatus sessionStatus;
}
