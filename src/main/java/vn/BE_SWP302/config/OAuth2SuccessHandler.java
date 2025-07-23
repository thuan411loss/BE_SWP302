package vn.BE_SWP302.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.response.ResLoginDTO;
import vn.BE_SWP302.repository.UserRepository;
import vn.BE_SWP302.util.SecurityUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import vn.BE_SWP302.repository.RoleRepository;
import vn.BE_SWP302.domain.Role;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Tạo user mới nếu chưa có
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(""); // Google user không cần password

            Role defaultRole = roleRepository.findByRoleName("Customer");
            user.setRole(defaultRole);
            userRepository.save(user);
            // Lấy lại user từ DB để đảm bảo có id, role...
            user = userRepository.findByEmail(email);
        }

        // Tạo userLogin DTO
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
        userLogin.setId(user.getId());
        userLogin.setEmail(user.getEmail());
        userLogin.setName(user.getName());
        if (user.getRole() != null) {
            userLogin.setRole(user.getRole().getRoleName());
        }

        // Tạo access token
        String accessToken = securityUtil.createAccessToken(email, userLogin);

        // Set access token vào cookie
        ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(false) // Để false khi dev local, production thì true
                .path("/")
                .maxAge(60 * 60 * 24)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Lưu access token vào session
        request.getSession().setAttribute("accessToken", accessToken);

        // Redirect về frontend
        String redirectUrl = "http://localhost:5173/auth/google/callback?success=true&email=" + email + "&token="
                + accessToken;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}