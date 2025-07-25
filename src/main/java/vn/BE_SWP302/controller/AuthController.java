package vn.BE_SWP302.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.request.LoginDTO;
import vn.BE_SWP302.domain.request.RegisterDTO;
import vn.BE_SWP302.domain.request.ResetPasswordDTO;
import vn.BE_SWP302.domain.response.ResLoginDTO;
import vn.BE_SWP302.domain.response.ResCreateUserDTO;
import vn.BE_SWP302.repository.RoleRepository;
import vn.BE_SWP302.service.UserService;
import vn.BE_SWP302.util.SecurityUtil;
import vn.BE_SWP302.util.annotation.ApiMessage;
import vn.BE_SWP302.util.error.IdinvaliadException;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtils;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityUtil securityUtils,
            UserService userService,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtils = securityUtils;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/auth/login/google")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // authentication khong luu pass nguoi dung
        // set thong tin nguoi dung đăng nhập vào context(có thể sử dụng sau này)
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResLoginDTO res = new ResLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(loginDto.getUsername());
        if (currentUserDB != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
            if (currentUserDB.getRole() != null) {
                userLogin.setRole(currentUserDB.getRole().getRoleName());
            }
            res.setUser(userLogin);

        }
        // create access token
        String access_token = this.securityUtils.createAccessToken(authentication.getName(), res.getUser());

        res.setAccessToken(access_token);
        // create refresh token

        String refresh_token = this.securityUtils.createRefreshToken(loginDto.getUsername(), res);

        // update user
        this.userService.updateUserToken(refresh_token, loginDto.getUsername());

        // set cookies
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(res);
    }

    @GetMapping("/auth/account")
    @ApiMessage("fetch account")
    public ResponseEntity<ResLoginDTO.UserLogin> GetAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        User currentUserDB = this.userService.handleGetUserByUsername(email);
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();

        if (currentUserDB != null) {
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
            if (currentUserDB.getRole() != null) {
                userLogin.setRole(currentUserDB.getRole().getRoleName());
            }
        }
        return ResponseEntity.ok().body(userLogin);
    }

    @GetMapping("/auth/refresh")
    @ApiMessage("Get USer by refresh token")
    public ResponseEntity<ResLoginDTO> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "abc") String refresh_token)
            throws IdinvaliadException {
        if (refresh_token.equals("abc")) {
            throw new IdinvaliadException("Ban khong co refresh token o cookie");
        }
        // check valid
        Jwt decodedToken = this.securityUtils.checkValidRefreshToken(refresh_token);
        String email = decodedToken.getSubject();
        // check user by token + email
        User currentUser = this.userService.getUserByRefreshTokenAndEmail(refresh_token, email);
        if (currentUser == null) {
            throw new IdinvaliadException("Refresh Token khong hop le");

        }
        // issue new token/set refresh token as cookies
        ResLoginDTO res = new ResLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(email);
        if (currentUserDB != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
            if (currentUserDB.getRole() != null) {
                userLogin.setRole(currentUserDB.getRole().getRoleName());
            }
            res.setUser(userLogin);

        }
        // create access token
        String access_token = this.securityUtils.createAccessToken(email, res.getUser());

        res.setAccessToken(access_token);
        // create refresh token

        String new_refresh_token = this.securityUtils.createRefreshToken(email, res);

        // update user
        this.userService.updateUserToken(new_refresh_token, email);

        // set cookies
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(res);
    }

    @PostMapping("/auth/logout")
    @ApiMessage("Logout User")
    public ResponseEntity<Void> logout() throws IdinvaliadException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        if (email.equals("")) {
            throw new IdinvaliadException("Access Token khong hop le");

        }
        // update refresh token = null
        this.userService.updateUserToken(email, email);

        // remove refresh token cookie
        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .body(null);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO) {

        if (userService.isEmailExist(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setAge(registerDTO.getAge());
        user.setAddress(registerDTO.getAddress());
        user.setPhone(registerDTO.getPhone());
        user.setGender(registerDTO.getGender());

        // Luôn tạo user với role Customer
        userService.handleCreateUserWithDefaultRole(user);

        userService.createUserAndAccount(user, user.getEmail(), user.getPassword());

        ResCreateUserDTO res = userService.convertToResCreateUserDTO(user);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/auth/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        // Kiểm tra user tồn tại, tạo mã OTP hoặc link reset, gửi email
        // Lưu mã OTP/link vào DB hoặc cache
        return ResponseEntity.ok("Đã gửi email xác nhận!");
    }

    @PostMapping("/auth/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO dto) {
        // Kiểm tra mã OTP hoặc link hợp lệ
        // Đổi mật khẩu mới cho user
        return ResponseEntity.ok("Đổi mật khẩu thành công!");
    }

    // lấy role có sẵn
    @GetMapping("/auth/roles")
    @ApiMessage("Get available roles for registration")
    public ResponseEntity<?> getAvailableRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

}
