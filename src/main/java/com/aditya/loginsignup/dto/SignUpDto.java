package com.aditya.loginsignup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SignUpDto {
    private String name;
    private String userName;
    private String email;
    private String password;
}
