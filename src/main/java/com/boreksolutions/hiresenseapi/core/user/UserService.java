package com.boreksolutions.hiresenseapi.core.user;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;
import com.boreksolutions.hiresenseapi.core.user.dto.request.UserFilter;
import com.boreksolutions.hiresenseapi.core.user.dto.response.UserDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User createUser(@Valid CreateUser createUser);

    UserDto findUserById(Long id);

    PageObject<UserDto> filter(UserFilter filter, Pageable pageable);

    void deleteUserById(Long id);

    void updateUser(UserDto userDto);
}


