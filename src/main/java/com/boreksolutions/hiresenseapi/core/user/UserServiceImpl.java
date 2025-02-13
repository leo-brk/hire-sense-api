package com.boreksolutions.hiresenseapi.core.user;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.config.exceptions.models.ErrorCode;
import com.boreksolutions.hiresenseapi.config.exceptions.models.NotFoundException;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;
import com.boreksolutions.hiresenseapi.core.user.dto.response.UserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public User createUser(CreateUser createUser) {
        User user = userMapper.dtoToEntity(createUser);
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setRole(createUser.getRole());

        return userRepository.save(user);
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getReason()));

        return userMapper.entityToDto(user);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found: " + email));

        return userMapper.entityToDto(user);
    }

    @Override
    public PageObject<UserDto> getUsersPage(Pageable pageable) {
        Page<User> users = userRepository.findAllUsers(pageable);

        return userMapper.pageToPageObject(users);

    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getReason()));

        userMapper.updateDtoToEntity(userDto, user);

        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        log.info("Deletion not implemented yet");
    }
}