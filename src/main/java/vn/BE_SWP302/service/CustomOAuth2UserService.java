package vn.BE_SWP302.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.response.ResLoginDTO;
import vn.BE_SWP302.repository.UserRepository;
import vn.BE_SWP302.util.SecurityUtil;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Lấy thông tin từ Google
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Nếu user chưa có trong DB thì lưu mới
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(""); // Google user không cần password
            userRepository.save(user);
        }

        // Tạo JWT token cho user
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
        userLogin.setId(user.getId());
        userLogin.setEmail(user.getEmail());
        userLogin.setName(user.getName());
        if (user.getRole() != null) {
            userLogin.setRole(user.getRole().getRoleName());
        }

        // Tạo access token
        String accessToken = securityUtil.createAccessToken(email, userLogin);

        // Lưu token vào session hoặc response header
        // Note: In a real implementation, you might want to store this differently

        return oAuth2User;
    }
}