package com.springbootjwedemo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AnythingDto {
    @NotBlank(message = "email required")
    @Email(message = "must be a valid email")
    String email;
    @NotBlank(message = "password required")
    @Size(min = 6, max = 25, message = "Invalid Password.")
    String password;
}