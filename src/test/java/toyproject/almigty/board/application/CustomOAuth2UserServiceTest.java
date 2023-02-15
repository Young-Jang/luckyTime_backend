//package toyproject.almigty.board.application;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import toyproject.almigty.board.domain.model.User;
//import toyproject.almigty.board.domain.repository.UserRepository;
//import toyproject.almigty.config.auth.OAuthAttributes;
//import toyproject.almigty.config.auth.SessionUser;
//
//import javax.servlet.http.HttpSession;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//class CustomOAuth2UserServiceTest {
//
//    @InjectMocks
//    private CustomOAuth2UserService customOAuth2UserService;
//
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private HttpSession httpSession;
//
//    @Test
//    void loadUserTest() throws Exception{
//        User user = User.builder()
//                .name("jy")
//                .email("b116104@naver.com")
//                .picture("image")
//                .build();
//        //given
//        given(userRepository.save(any())).willReturn(user);
//        //when
//        //then
//    }
//}