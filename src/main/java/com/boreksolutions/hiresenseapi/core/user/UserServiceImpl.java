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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
        User user = userRepository.findByIdAndDeletedAtIsNull(id) // Fetch only non-deleted users
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getReason()));
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmailAndDeletedAtIsNull(email) // Fetch only non-deleted users
                .orElseThrow(() -> new NotFoundException("User not found: " + email));
        return userMapper.entityToDto(user);
    }

    @Override
    public PageObject<UserDto> getUsersPage(Pageable pageable) {
        Page<User> users = userRepository.findAllUsers(pageable); // Fetch only non-deleted users
        return userMapper.pageToPageObject(users);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userDto.getId()) // Update only non-deleted users
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getReason()));
        userMapper.updateDtoToEntity(userDto, user);
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailAndDeletedAtIsNull(email); // Check only non-deleted users
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getReason()));

        if (user.getDeletedAt() != null) {
            throw new NotFoundException("User with ID " + id + " is already deleted");
        }

        user.setDeletedAt(Timestamp.valueOf(LocalDateTime.now())); // Mark as deleted
        log.info("User with ID {}, email {} marked as deleted", id, user.getEmail());
    }


    // Fetch active users (non-deleted)
    public User findActiveUserById(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getReason()));
    }

    // Fetch all deleted users
    //TODO Remove
    public List<User> findAllDeletedUsers() {
        return userRepository.findByDeletedAtIsNotNull();
    }
}