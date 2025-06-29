package vn.BE_SWP302.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import vn.BE_SWP302.domain.Role;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.request.ChangeRoleDTO;
import vn.BE_SWP302.domain.response.ResCreateUserDTO;
import vn.BE_SWP302.domain.response.ResAdminUserDTO;
import vn.BE_SWP302.repository.RoleRepository;
import vn.BE_SWP302.service.UserService;
import vn.BE_SWP302.util.annotation.ApiMessage;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Tạo admin đầu tiên (chỉ dùng 1 lần)
    @PostMapping("/create-first-admin")
    @ApiMessage("Create first admin")
    public ResponseEntity<?> createFirstAdmin(@RequestBody CreateFirstAdminDTO request) {
        try {
            // Encode password
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            User admin = userService.createFirstAdmin(request.getEmail(), encodedPassword, request.getName());
            ResCreateUserDTO res = userService.convertToResCreateUserDTO(admin);
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Lấy danh sách tất cả roles
    @GetMapping("/roles")
    @ApiMessage("Get all available roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    // Lấy danh sách users theo role
    @GetMapping("/users/role/{roleName}")
    @ApiMessage("Get users by role")
    public ResponseEntity<List<ResAdminUserDTO>> getUsersByRole(@PathVariable String roleName) {
        List<User> users = userService.getUsersByRole(roleName);
        List<ResAdminUserDTO> userDTOs = users.stream()
                .map(userService::convertToResAdminUserDTO)
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    // Thay đổi role của user
    @PutMapping("/users/change-role")
    @ApiMessage("Change user role")
    public ResponseEntity<?> changeUserRole(@Valid @RequestBody ChangeRoleDTO changeRoleDTO) {
        try {
            User updatedUser = userService.changeUserRole(changeRoleDTO.getUserId(), changeRoleDTO.getRoleId());
            ResAdminUserDTO res = userService.convertToResAdminUserDTO(updatedUser);
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Lấy thông tin user cụ thể
    @GetMapping("/users/{userId}")
    @ApiMessage("Get user by ID")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        User user = userService.fetchUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        ResAdminUserDTO res = userService.convertToResAdminUserDTO(user);
        return ResponseEntity.ok(res);
    }

    // Inner class cho DTO tạo admin đầu tiên
    public static class CreateFirstAdminDTO {
        private String email;
        private String password;
        private String name;

        // Getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}