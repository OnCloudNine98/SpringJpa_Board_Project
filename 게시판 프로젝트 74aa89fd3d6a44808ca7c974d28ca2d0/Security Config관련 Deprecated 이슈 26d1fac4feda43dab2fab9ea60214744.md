# Security Config관련 Deprecated 이슈

기존의 **Spring Security** 를 사용하면 기본적인 설정을 위해 `SecurityConfig` 클래스에서 `WebSecurityConfigurerAdapter`추상 클래스를 상속하고 `Configure` 메서드를 오버라이드 하여 설정 하였지만 5.7.0-M2 버전 부터는  `WebSecurityConfigurerAdapter` 가 deprecated 되어 다른 형식으로 설정 해줍니다. (상속 구조가 없어져 override 형식이 새롭게 바뀜)

**5.7.0-M2 이전**

![Untitled](Security%20Config%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB%20Deprecated%20%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B2%2026d1fac4feda43dab2fab9ea60214744/Untitled.png)

**5.7.0-M2 이후**

![Untitled](Security%20Config%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB%20Deprecated%20%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B2%2026d1fac4feda43dab2fab9ea60214744/Untitled%201.png)

## Deprecated 로 인한 변경 내용

- **`configure` 메서드 에서 `filterChain` 메서드로 변경**
    
    **변경 전**
    
    ![Untitled](Security%20Config%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB%20Deprecated%20%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B2%2026d1fac4feda43dab2fab9ea60214744/Untitled%202.png)
    
    **변경 후**
    
    ![Untitled](Security%20Config%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB%20Deprecated%20%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B2%2026d1fac4feda43dab2fab9ea60214744/Untitled%203.png)
    
- **그 외 `configure` 메서드 변경과 다른 오버라이드 메서드 변경**
    
    **변경 전**
    
    ![Untitled](Security%20Config%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB%20Deprecated%20%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B2%2026d1fac4feda43dab2fab9ea60214744/Untitled%204.png)
    
    **변경 후**
    
    ![Untitled](Security%20Config%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB%20Deprecated%20%E1%84%8B%E1%85%B5%E1%84%89%E1%85%B2%2026d1fac4feda43dab2fab9ea60214744/Untitled%205.png)
    
    - `@EnableWebSecurity`
        - `@EnableGlobalMethodSecurity(prePostEnabled = true)` 가
        스프링 부트 3.2 버전 부터 deprecated 되어 `@EnableWebSecurity` 을 통해 사용을 대체
    - `authenticationManager(AuthenticationConfiguration authenticationConfiguration)`
    - `authenticationProvider(AuthenticationManagerBuilder auth)`
    - `configure()`
        - `.antMatchers()` 는 deprecated 되어 `.requestMatchers()` 사용

---

### Security 관련 버전 업 과정중 발생한 문제

- Fragment 관련
    
    ### 문제 발생 화면
    
    **원래 의도의 화면**
    
    ![Untitled](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134/Untitled.png)
    
    **소스 코드**
    
    ![Untitled](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134/Untitled%201.png)
    
    **출력 화면**
    
    ![Untitled](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134/Untitled%202.png)
    
    ### 원인 분석
    
    화면의 표면적인 차이는 Bootstrap이 적용 되냐 안되냐의 차이 였다.
    이는 좀더 본질적으로 접근하면 ThymeLeaf 의 Fragment 개념적용의 문제 였다.
    
- Ajax 관련
    
    ### 에러 내용
    
    ### 원인 분석
    
    기존 mustache는 템플릿 코드에서 `<button>` 요소에 `action` 태그가 없더라도, 버튼을 눌렀을 때 페이지가 이동하거나 특정 동작이 수행하는 반면
    Thymeleaf 는 `<button>` 요소에 `action` 태그없으면 직접 JavaScript 이벤트 핸들러를 통해 전송해야 한다.
    
- Thymeleaf 파라미터 관련
    
    <aside>
    💡 Thymeleaf에서 `th:if` 속성에 사용하는 표현식은 기본적으로 모델에서 제공하는 변수를 참조
    하기 때문에 모델을 명시적으로 언급할 필요 없이 표현식으로 직접 사용하면 된다.
    
    </aside>
    
    ```java
    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable("id") Long id, @LoginUser UserDto.Response user, Model model) {
     if (comments.stream().anyMatch(s -> s.getUserId().equals(user.getId()))) {
              model.addAttribute("isWriter", true);
    
          }
                  return "posts/posts-read";
    }
    ```
    
    ```html
    ...
    <span th:if="${isWriter}"> ... </span>
    ...
    ```
    

---

### 참고 출처

[https://covenant.tistory.com/277#google_vignette](https://covenant.tistory.com/277#google_vignette)

[https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring](https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring)

[https://www.inflearn.com/questions/812490/spring-security-최신버전-spring-boot-3-x-x-대-의-websecurity-설정-공유드립니다](https://www.inflearn.com/questions/812490/spring-security-%EC%B5%9C%EC%8B%A0%EB%B2%84%EC%A0%84-spring-boot-3-x-x-%EB%8C%80-%EC%9D%98-websecurity-%EC%84%A4%EC%A0%95-%EA%B3%B5%EC%9C%A0%EB%93%9C%EB%A6%BD%EB%8B%88%EB%8B%A4)

[https://stackoverflow.com/questions/23669424/cant-create-csrf-token-with-spring-security](https://stackoverflow.com/questions/23669424/cant-create-csrf-token-with-spring-security)