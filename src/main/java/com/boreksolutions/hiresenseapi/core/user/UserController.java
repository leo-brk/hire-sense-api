package com.boreksolutions.hiresenseapi.core.user;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.user.dto.request.CreateUser;
import com.boreksolutions.hiresenseapi.core.user.dto.response.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUser createUser) {
        return ResponseEntity.ok(userService.createUser(createUser).getId());
    }

    //TODO Change to filter
    @GetMapping
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    //TODO Remove
    @GetMapping("/list")
    public ResponseEntity<PageObject<UserDto>> getUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersPage(pageable));
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return ResponseEntity.noContent().build();
    }


    // Soft delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<UserDto>> getAllDeletedUsers() {
        List<User> deletedUsers = ((UserServiceImpl) userService).findAllDeletedUsers();
        List<UserDto> userDtos = deletedUsers.stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole(),
                        user.getEnabled()
                )) // Map User to UserDto directly
                .toList();
        return ResponseEntity.ok(userDtos);
    }

}
