package vn.BE_SWP302.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Lấy thông tin từ Google
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // Nếu user chưa có trong DB thì lưu mới
        if (userRepository.findByEmail(email) == null) {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(""); // Google user không cần password
            userRepository.save(user);
        }

        return oAuth2User;
    }
}