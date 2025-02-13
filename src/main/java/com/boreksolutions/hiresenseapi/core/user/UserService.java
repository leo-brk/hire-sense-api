package com.boreksolutions.hiresenseapi.core.user;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;
import com.boreksolutions.hiresenseapi.core.user.dto.response.UserDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User createUser(@Valid CreateUser createUser);

    UserDto findUserById(Long id);

    UserDto findUserByEmail(String email);

    PageObject<UserDto> getUsersPage(Pageable pageable);

    void deleteUserById(Long id);

    void updateUser(UserDto userDto);

    Boolean existsByEmail(String email);
}