package vn.BE_SWP302.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.Role;
import vn.BE_SWP302.domain.request.Meta;
import vn.BE_SWP302.domain.request.ResultPaginationDTO;
import vn.BE_SWP302.domain.response.ResCreateUserDTO;
import vn.BE_SWP302.domain.response.ResUpdateUserDTO;
import vn.BE_SWP302.domain.response.ResUserDTO;
import vn.BE_SWP302.domain.response.ResAdminUserDTO;
import vn.BE_SWP302.repository.UserRepository;
import vn.BE_SWP302.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User handleCreateUser(User user) {
        return userRepository.save(user);
    }

    // Method để tạo user với role mặc định (Customer)
    public User handleCreateUserWithDefaultRole(User user) {
        // Tìm role Customer mặc định
        Role defaultRole = roleRepository.findByRoleName("Customer");
        if (defaultRole == null) {
            // Nếu không có role Customer, tạo mới
            defaultRole = new Role();
            defaultRole.setRoleName("Customer");
            defaultRole = roleRepository.save(defaultRole);
        }
        user.setRole(defaultRole);
        return userRepository.save(user);
    }

    // Method để tạo user với role cụ thể
    public User handleCreateUserWithRole(User user, Long roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role != null) {
            user.setRole(role);
        }
        return userRepository.save(user);
    }

    // Method để tạo user với role name
    public User createUserWithRole(String email, String password, String name, String roleName) {
        // Tìm role theo tên
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            throw new RuntimeException("Role '" + roleName + "' không tồn tại");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setRole(role);
        user.setAge(0); // Có thể set sau

        return userRepository.save(user);
    }

    public void handleDeleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public User fetchUserById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null;
        }
    }

    public ResultPaginationDTO fetchAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());

        rs.setMeta(mt);
        List<ResUserDTO> listUser = pageUser.getContent()
                .stream().map(item -> this.convertToResUserDTO(item))
                .collect(Collectors.toList());

        rs.setResult(listUser);
        return rs;
    }

    public User handleUpdateUser(User reqUser) {
        User currentUser = this.fetchUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setName(reqUser.getName());
            currentUser.setEmail(reqUser.getEmail());
            // Chỉ update password nếu FE truyền lên
            if (reqUser.getPassword() != null && !reqUser.getPassword().isBlank()) {
                currentUser.setPassword(reqUser.getPassword());
            }
            currentUser.setPhone(reqUser.getPhone());
            currentUser.setAge(reqUser.getAge());
            currentUser.setGender(reqUser.getGender());
            currentUser.setAddress(reqUser.getAddress());
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO res = new ResCreateUserDTO();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setCreatedAt(user.getCreatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        res.setPhone(user.getPhone());

        // Thêm thông tin role
        if (user.getRole() != null) {
            res.setRoleId(user.getRole().getRoleId());
            res.setRoleName(user.getRole().getRoleName());
        }

        return res;
    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setCreatedAt(user.getCreatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        res.setPhone(user.getPhone());

        return res;
    }

    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        res.setPhone(user.getPhone());

        return res;
    }

    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }

    // Method để thay đổi role của user
    public User changeUserRole(Long userId, Long roleId) {
        User user = fetchUserById(userId);
        if (user == null) {
            throw new RuntimeException("User không tồn tại");
        }

        Role newRole = roleRepository.findById(roleId).orElse(null);
        if (newRole == null) {
            throw new RuntimeException("Role không tồn tại");
        }

        user.setRole(newRole);
        return userRepository.save(user);
    }

    // Method để lấy danh sách users theo role
    public List<User> getUsersByRole(String roleName) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() != null &&
                        user.getRole().getRoleName().equalsIgnoreCase(roleName))
                .collect(Collectors.toList());
    }

    public List<String> getDoctorNames() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() != null &&
                        user.getRole().getRoleName().equalsIgnoreCase("Doctor"))
                .map(User::getName)
                .collect(Collectors.toList());
    }

    // Method để tạo admin đầu tiên
    public User createFirstAdmin(String email, String password, String name) {
        // Kiểm tra xem đã có admin nào chưa
        List<User> admins = getUsersByRole("Admin");
        if (!admins.isEmpty()) {
            throw new RuntimeException("Đã có admin trong hệ thống");
        }

        // Tìm role Admin
        Role adminRole = roleRepository.findByRoleName("Admin");
        if (adminRole == null) {
            // Tạo role Admin nếu chưa có
            adminRole = new Role();
            adminRole.setRoleName("Admin");
            adminRole = roleRepository.save(adminRole);
        }

        User admin = new User();
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setName(name);
        admin.setRole(adminRole);
        admin.setAge(0); // Có thể set sau

        return userRepository.save(admin);
    }

    // Method convert dành riêng cho admin
    public ResAdminUserDTO convertToResAdminUserDTO(User user) {
        ResAdminUserDTO res = new ResAdminUserDTO();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setCreatedAt(user.getCreatedAt());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        res.setPhone(user.getPhone());
        res.setCreatedBy(user.getCreatedBy());
        res.setUpdatedBy(user.getUpdatedBy());

        // Thông tin role
        if (user.getRole() != null) {
            res.setRoleId(user.getRole().getRoleId());
            res.setRoleName(user.getRole().getRoleName());
        }

        return res;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
