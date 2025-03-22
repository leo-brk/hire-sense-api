package com.boreksolutions.hiresenseapi.core.user;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;
import com.boreksolutions.hiresenseapi.core.user.dto.request.UserFilter;
import com.boreksolutions.hiresenseapi.core.user.dto.response.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUser createUser) {
        return ResponseEntity.ok(userService.createUser(createUser).getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageObject<UserDto>> filter(@RequestBody UserFilter userFilter, Pageable pageable) {
        return ResponseEntity.ok(userService.filter(userFilter, pageable));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}