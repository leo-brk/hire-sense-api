package com.boreksolutions.hiresenseapi.core.user;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.config.exceptions.models.ErrorCode;
import com.boreksolutions.hiresenseapi.config.exceptions.models.NotFoundException;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;
import com.boreksolutions.hiresenseapi.core.user.dto.request.UserFilter;
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

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserCriteriaBuilder criteriaQuery;

    @Override
    public User createUser(CreateUser createUser) {
        User user = userMapper.dtoToEntity(createUser);
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setRole(createUser.getRole());
        return userRepository.save(user);
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getReason()));
        return userMapper.entityToDto(user);
    }

    @Override
    public PageObject<UserDto> filter(UserFilter filter, Pageable pageable) {
        Page<User> users = criteriaQuery.filterUsers(filter, pageable);
        return userMapper.pageToPageObject(users);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getReason()));
        userMapper.updateDtoToEntity(userDto, user);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getReason()));
        user.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
    }
}