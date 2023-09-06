package hgcha.CodeAgora.config;

import hgcha.CodeAgora.oauth.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(
            formLogin -> formLogin.loginPage("/loginForm")
                                  .loginProcessingUrl("/login")
                                  .failureHandler(((request, response, exception) -> {
                    if (exception instanceof LockedException) {
                        response.sendRedirect("/loginForm?error=locked&username=" + request.getParameter("username"));
                    }
                })));

        http.logout(logout -> logout.logoutUrl("/logout")
                                    .logoutSuccessUrl("/")
                                    .invalidateHttpSession(true));

        http.oauth2Login(oauth2login -> oauth2login.loginPage("/loginForm")
                                                   .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2UserService)));

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
