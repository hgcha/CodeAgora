package hgcha.CodeAgora.oauth;

import hgcha.CodeAgora.auth.PrincipalDetails;
import hgcha.CodeAgora.domain.user.entity.User;
import hgcha.CodeAgora.domain.user.repository.UserRepository;
import hgcha.CodeAgora.domain.user.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserInfo oAuth2UserInfo;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleOAuth2UserInfo(super.loadUser(userRequest).getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverOAuth2UserInfo(super.loadUser(userRequest).getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoOAuth2UserInfo(super.loadUser(userRequest).getAttributes());
        } else {
            throw new OAuth2AuthenticationException("올바른 프로바이더가 아닙니다.");
        }

        User user = userRepository.findByUsername(oAuth2UserInfo.getUsername()).orElseGet(() ->
            userRepository.save(User.builder()
                                    .username(oAuth2UserInfo.getUsername())
                                    .password(null)
                                    .email(oAuth2UserInfo.getEmail())
                                    .role(Role.ROLE_USER)
                                    .build()));

        return new PrincipalDetails(user, oAuth2UserInfo.getAttributes());
    }
}
