package com.boreksolutions.hiresenseapi.core.user.dto.response;

import com.boreksolutions.hiresenseapi.core.user.Role;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Positive
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private Boolean isEnabled;
}