package com.boreksolutions.hiresenseapi.core.user.dto.request;

import lombok.Data;

@Data
public class UserFilter {

    private String firstName;

    private String lastName;

    private String email;
}
