package com.boreksolutions.hiresenseapi.core.user.dto.request;

import com.boreksolutions.hiresenseapi.config.validation.uniqueUsername.UniqueUsername;
import com.boreksolutions.hiresenseapi.core.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser {

    @NotBlank(message = "First name can not be blank")
    String firstName;

    @NotBlank(message = "Last name can not be blank")
    String lastName;

    @Email(message = "Must me of type email")
    @NotBlank(message = "Email can not be blank")
    @UniqueUsername(message = "Email already exists")
    String email;

    @Size(min = 3, max = 18, message = "Password must be between 3 and 18 characters long")
    String password;

    @NotNull(message = "Role cannot be null")
    private Role role;
}