package toyProject.SpringbootBoard.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import toyProject.SpringbootBoard.application.security.auth.CustomUserDetailsService;
import toyProject.SpringbootBoard.application.security.oauth.CustomOAuth2UserService;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
// 특정 주소로 접근하면 권한 및 인증을 미리 체크
public class SecurityConfig {
    /* 세션 관련 로그인 핸들러*/
    private final CustomUserDetailsService customUserDetailsService;

    /* 로그인 실패 핸들러*/
    private final AuthenticationFailureHandler customFailureHandler;

    /* OAuth2 관련 핸들러*/
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    /* 시큐리티가 로그인 과정에서 password를 가로챌때 어떤 해쉬로 암호화 했는지 확인 */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }


    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web
                .ignoring()
                .requestMatchers("/css/**", "/js/**", "/img/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf((auth) -> auth
                        .ignoringRequestMatchers("/api/**") /* REST API 사용 예외처리 */
                )
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/auth/**", "/posts/read/**", "/posts/search/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((auth) -> auth
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/loginProc")
                        .failureHandler(customFailureHandler)
                        .defaultSuccessUrl("/")
                )
                .logout((auth) -> auth
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                )
                .oauth2Login((auth) -> auth
                        .userInfoEndpoint(userinfo -> userinfo
                                .userService(customOAuth2UserService) // 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
                        ) // OAuth2 로그인 성공 후 가져올 설정들
                );
        return http.build();
    }


}
