package vn.BE_SWP302.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Account;
import vn.BE_SWP302.domain.Role;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.request.ChangeRoleDTO;
import vn.BE_SWP302.domain.request.CreateFirstAdminDTO;
import vn.BE_SWP302.domain.response.ResCreateUserDTO;
import vn.BE_SWP302.domain.response.UserAdminResponse;
import vn.BE_SWP302.domain.response.ResAdminUserDTO;
import vn.BE_SWP302.repository.AccountRepository;
import vn.BE_SWP302.repository.RoleRepository;
import vn.BE_SWP302.service.UserService;
import vn.BE_SWP302.util.annotation.ApiMessage;
import vn.BE_SWP302.util.error.IdinvaliadException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    // Tạo admin đầu tiên (chỉ dùng 1 lần)
    @PostMapping("/create-first-admin")
    @ApiMessage("Create first admin")
    public ResponseEntity<?> createFirstAdmin(@Valid @RequestBody CreateFirstAdminDTO request)
            throws IdinvaliadException {
        try {
            // Kiểm tra email đã tồn tại chưa
            if (userService.isEmailExist(request.getEmail())) {
                throw new IdinvaliadException("Email " + request.getEmail() + " đã tồn tại trong hệ thống");
            }

            // Encode password
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            User admin = userService.createFirstAdmin(request.getEmail(), encodedPassword, request.getName());
            ResCreateUserDTO res = userService.convertToResCreateUserDTO(admin);
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            throw new IdinvaliadException(e.getMessage());
        }
    }

    // Lấy thông tin admin hiện tại
    @GetMapping("/admin-info")
    @ApiMessage("Get current admin info")
    public ResponseEntity<?> getAdminInfo() throws IdinvaliadException {
        List<User> admins = userService.getUsersByRole("Admin");
        if (admins.isEmpty()) {
            throw new IdinvaliadException("Chưa có admin nào trong hệ thống");
        }

        // Lấy admin đầu tiên (vì chỉ có 1 admin)
        User admin = admins.get(0);
        ResAdminUserDTO res = userService.convertToResAdminUserDTO(admin);
        return ResponseEntity.ok(res);
    }

    // Lấy danh sách tất cả roles
    @GetMapping("/roles")
    @ApiMessage("Get all available roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    // Lấy danh sách users theo role
    @GetMapping("/users/role/{roleName}")
    @ApiMessage("Get users by role")
    public ResponseEntity<List<ResAdminUserDTO>> getUsersByRole(@PathVariable String roleName)
            throws IdinvaliadException {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IdinvaliadException("Role name không được để trống");
        }

        // Kiểm tra role có tồn tại không
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            throw new IdinvaliadException("Role '" + roleName + "' không tồn tại trong hệ thống");
        }

        List<User> users = userService.getUsersByRole(roleName);
        List<ResAdminUserDTO> userDTOs = users.stream()
                .map(userService::convertToResAdminUserDTO)
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    // Thay đổi role của user
    @PutMapping("/users/change-role")
    @ApiMessage("Change user role")
    public ResponseEntity<?> changeUserRole(@Valid @RequestBody ChangeRoleDTO changeRoleDTO)
            throws IdinvaliadException {
        try {
            // Validate input
            if (changeRoleDTO.getUserId() == null) {
                throw new IdinvaliadException("User ID không được để trống");
            }
            if (changeRoleDTO.getRoleId() == null) {
                throw new IdinvaliadException("Role ID không được để trống");
            }

            // Kiểm tra user có tồn tại không
            User existingUser = userService.fetchUserById(changeRoleDTO.getUserId());
            if (existingUser == null) {
                throw new IdinvaliadException("User với ID " + changeRoleDTO.getUserId() + " không tồn tại");
            }

            // Kiểm tra role có tồn tại không
            Role existingRole = roleRepository.findById(changeRoleDTO.getRoleId()).orElse(null);
            if (existingRole == null) {
                throw new IdinvaliadException("Role với ID " + changeRoleDTO.getRoleId() + " không tồn tại");
            }

            // Bảo vệ admin duy nhất - không cho phép thay đổi role của admin
            if (existingUser.getRole() != null && "Admin".equalsIgnoreCase(existingUser.getRole().getRoleName())) {
                throw new IdinvaliadException("Không thể thay đổi role của admin duy nhất trong hệ thống");
            }

            // Không cho phép tạo thêm admin
            if ("Admin".equalsIgnoreCase(existingRole.getRoleName())) {
                List<User> admins = userService.getUsersByRole("Admin");
                if (!admins.isEmpty()) {
                    throw new IdinvaliadException("Hệ thống chỉ cho phép 1 admin duy nhất");
                }
            }

            User updatedUser = userService.changeUserRole(changeRoleDTO.getUserId(), changeRoleDTO.getRoleId());
            ResAdminUserDTO res = userService.convertToResAdminUserDTO(updatedUser);
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            throw new IdinvaliadException(e.getMessage());
        }
    }

    // Lấy thông tin user cụ thể
    @GetMapping("/users/{userId}")
    @ApiMessage("Get user by ID")
    public ResponseEntity<ResAdminUserDTO> getUserById(@PathVariable Long userId) throws IdinvaliadException {
        if (userId == null) {
            throw new IdinvaliadException("User ID không được để trống");
        }

        User user = userService.fetchUserById(userId);
        if (user == null) {
            throw new IdinvaliadException("User với ID " + userId + " không tồn tại");
        }

        ResAdminUserDTO res = userService.convertToResAdminUserDTO(user);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/users")
    @ApiMessage("Get all users")
    public ResponseEntity<List<UserAdminResponse>> getAllUsers() {
        List<UserAdminResponse> users = userService.getAllUsersForAdmin();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete user by ID")
    public void deleteUserByAdmin(@PathVariable Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setIsActive(false);
        accountRepository.save(account);
    }

    @GetMapping("/users/search")
    @ApiMessage("Search users by keyword")
    public ResponseEntity<List<UserAdminResponse>> searchUsers(@RequestParam("q") String keyword) {
        List<UserAdminResponse> users = userService.searchUsersForAdmin(keyword);
        return ResponseEntity.ok(users);
    }

}