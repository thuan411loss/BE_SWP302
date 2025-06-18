package vn.BE_SWP302.controller;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.dto.ResUpdateUserDTO;
import vn.BE_SWP302.domain.dto.ResUserDTO;
import vn.BE_SWP302.domain.dto.ResultPaginationDTO;
import vn.BE_SWP302.service.UserService;
import vn.BE_SWP302.util.annotation.ApiMessage;
import vn.BE_SWP302.util.error.IdinvaliadException;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    @ApiMessage("create new user")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody User postManUser)
            throws IdinvaliadException {
        boolean isEmailExist = this.userService.isEmailExist(postManUser.getEmail());
        if (isEmailExist) {
            throw new IdinvaliadException(
                    "Email " + postManUser.getEmail() + " da ton tai trong he thong. Vui long chon email khac.");
        }
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User ericUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(ericUser));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("delete user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id)
            throws IdinvaliadException {
        User currentUser = this.userService.fetchUserById(id);
        if (currentUser == null) {
            throw new IdinvaliadException("User with id " + id + " does not exist.");
        }

        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") Long id) throws IdinvaliadException {
        User ericUser = this.userService.fetchUserById(id);
        if (ericUser == null) {
            throw new IdinvaliadException("User with id " + id + " does not exist.");
        }
        return ResponseEntity.ok(this.userService.convertToResUserDTO(ericUser));
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUsers(
            @Filter Specification<User> spec,
            Pageable pageable) {

        return ResponseEntity.ok(this.userService.fetchAllUsers(spec, pageable));

    }

    @PutMapping("users")
    @ApiMessage("update user")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user)
            throws IdinvaliadException {
        User ericUser = this.userService.handleUpdateUser(user);
        if (ericUser == null) {
            throw new IdinvaliadException("User with id " + user.getId() + " does not exist.");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(ericUser));
    }
}