# SecurityConfig.java

```java
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
```

1. **`@EnableWebSecurity`**
    - @Configuration 에 추가해 Spring Security 설정을 활성화 하는 어노테이션
    
2. **`encoder()`**
    - Spring Security 에서 제공하는 클래스인  BCryptPasswordEncoder 를 이용하여 Bcrypt 해싱 알고리즘을 사용한 암호를 사용하기 위해 Bean으로 등록
    
3. **`authenticationManager(AuthenticationConfiguration authenticationConfiguration)`**
    - Spring Security Context의 인증 관리자(**authenticationManager)**를 가져와 스프링 빈에 등록
    
4. **`authenticationProvider(AuthenticationManagerBuilder auth)`**
    - SpringSecurity 의 인증처리를 이용하기 위해 `AuthenticationManager` 를 생성하는 `AuthenticationManagerBuilder` 사용
    - DaoAuthenticationProvider 라는 클래스의 Data Access Object 를 통해 정보를 가져와 인증을 처리하는데 사용
    - 로그인 인증과 관련된 `customUserDetailsService` 와 
    비밀번호 암호화 와 관련된 `BCryptPasswordEncoder` 의 `encoder()` 를
    DAO 에 설정하여 return.
    
5. **`configure()`** 
    - 인증을 무시할 경로 설정
    - static 하위 폴더(`css/js/img`) 는 무조건 접근이 가능해야 하기 때문에 인증을 무시
    
6. **`filterChain(HttpSecurity http)`**
    - HttpSecurity 를 통해 HTTP요청에 대한 보안을 구성
    - `.csrf` : 정상적인 사용자가 **의도치 않은 위조요청을 보내는 CSRF** 에 관한 설정 정의
        - `.ignoringRequestMatchers()`  
        : CSRF Token 사용을 부분 **비활성화시킬 url을 설정** 메서드
    - `.authorizeHttpRequests` :  HttpServletRequest 에 따라 접근을 제한을 설정 정의
        - `.RequestMatchers()` : 인증 인가 없이 **접근을 허용** 메서드
        - `.anyRequest().authenticated()` 메서드로 지정된 URL외 요청에 대하여 진입하기 위해선 **인증이 필요함을 설정** 메서드
    - `.formLogin` : Form Login 방식에 관한 설정 정의
        - `.loginPage()` : **로그인 페이지 URL 설정** 메서드
        - `.loginProcessingUrl()` : 로그인 폼이 제출될 때 **처리할 URL을 설정**하는 메서드
            
            > 사용자가 로그인 폼을 제출하면 Spring Security 는 
            해당 URL 로 POST 요청을 처리하여 인증을 수행
            > 
        - `.failureHandler()` : 로그인 **실패 시** 이동할 URL 메서드
        - `.defaultSuccessUrl("/")`  : 로그인 **성공 시** 이동할 URL 메서드
    - `.logout` : 로그아웃에 관한 설정 정의
        - `.logoutRequestMatcher()` : 로그아웃 요청을 매칭할 URL 패턴 설정 메서드
        - `.deleteCookies()` : 로그아웃 당시 **삭제할 특정 쿠키 설정** 메서드
        - `.logoutSuccessUrl()` : 로그아웃 **성공 시 이동할 URL 설정** 메서드
    - `.oauth2Login` : OAuth 2.0 기반의 로그인 설정 정의
        - `.userInfoEndpoint()` : OAuth 2.0 프로토콜에서 사용자 정보를 가져오는 엔드포인트 
        설정 메서드
            
            > 주로 `.userService()`  와 사용되어 OAuth 2.0 로그인 과정의 사용자 정보를 처리
            > 
        - `.userService()` : OAuth 2.0 로그인의 사용자 정보 처리 서비스 설정 메서드