# Spring Security 를 이용한 회원가입 및 로그인

기존의 직접 로그인을 처리하는 방식이 아닌 Spring Security 를 이용해 로그인 접근.

<aside>
💡 Spring Security?
스프링 프레임워크 기반 Applicatoin 을 보완하는 목적의 사용자 정의가 가능한 Access 제어 프레임워크. 자바 Application의 인증과 권한을 동시에 제공하는데 초점을 둠.

</aside>

### 구현 화면_회원가입

회원가입 화면

![Untitled](Spring%20Security%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%B8%20%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%201cb8b41398094b2da5fe7c9f5cc213ad/Untitled.png)

**가입 완료후 로그인 화면**

![Untitled](Spring%20Security%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%B8%20%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%201cb8b41398094b2da5fe7c9f5cc213ad/Untitled%201.png)

**로그인 완료 후의 화면**

![Untitled](Spring%20Security%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%B8%20%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%201cb8b41398094b2da5fe7c9f5cc213ad/Untitled%202.png)

### 구현 화면_로그인

**게시글과 다른 계정으로 로그인**

![Untitled](Spring%20Security%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%B8%20%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%201cb8b41398094b2da5fe7c9f5cc213ad/Untitled%203.png)

**게시글 상세보기는 가능 하지만 글의 수정 삭제는 불가**

![Untitled](Spring%20Security%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%B8%20%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%201cb8b41398094b2da5fe7c9f5cc213ad/Untitled%204.png)

**해당 게시글의 계정 로그인**

![Untitled](Spring%20Security%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%B8%20%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%201cb8b41398094b2da5fe7c9f5cc213ad/Untitled%205.png)

**게시글의 상세정보 뿐 아니라 수정과 삭제도 가능.**

![Untitled](Spring%20Security%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%B8%20%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%201cb8b41398094b2da5fe7c9f5cc213ad/Untitled%206.png)

### **접근 로직**

**회원가입**

1. 회원가입 화면에서 정보를 입력하고 ‘가입’ 버튼을 누르면
2. 매핑된 컨트롤러 에서 정보가 **잘못** 됐을 때(유효성 검사 결과)는 기존 화면으로,
정보에 **문제가 없을때**는 `userService` 의 메서드를 통해 가입 후 로그인 화면으로 redirect

**로그인**

1. 로그인 화면에서 아이디/비밀번호를 입력하고 ‘로그인’ 버튼을 누르면
2. `SecurityConfig` 에서 해당하는 URL에 관하여 로그인 처리

---

### 설정(build.gradle)

```
implementation 'org.springframework.boot:spring-boot-starter-security'
```

---

[User.java ](%E1%84%83%E1%85%A9%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB(Entity)%204baede46aea54d9e8dd03bfd1914be3e.md)

[Role.java ](%E1%84%83%E1%85%A9%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB(Entity)%204baede46aea54d9e8dd03bfd1914be3e.md) 

[UserDto.java](%E1%84%8B%E1%85%A6%E1%86%AB%E1%84%90%E1%85%B5%E1%84%90%E1%85%B5%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB%20DTO%20e4796b1ecaac44c5b495db7268744b97.md) 

---

### layout/header.html

```html
<html xmlns:th="http://www.thymeleaf.org"
th:fragment="header">

<!--탬플릿 레이아웃 사용-->
<head ><!--th:fragment="head"-->
    <title>Board Service</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/app.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div  id="header" class="d-flex bd-highlight"> <!--th:fragment="header"-->
    <a href="/" class="p-2 flex-grow-1 bd-highlight">Board Service</a>
    <form action="/posts/search" method="GET" class="form-inline p-2 bd-highlight" role="search">
        <input type="text" name="keyword" class="form-control" id="search" placeholder="검색">
        <button class="btn btn-success bi bi-search"></button>
    </form>
</div>

<nav  id="nav"> <!--th:fragment="nav"-->
    <div class="text-right">
        <div th:if="${user}">
            <span class="mx-3" th:text="${user.nickname + '님 안녕하세요!'}"></span>
            <a href="/logout" class="btn btn-outline-dark">로그아웃</a>
            <a href="/modify" class="btn btn-outline-dark bi bi-gear"></a>
        </div>
        <div th:unless="${user}">
            <a href="/oauth2/authorization/naver" role="button" class="btn btn-outline-success"><img id="img" src="/img/naver.ico"/> 로그인</a>
            <a href="/oauth2/authorization/google" role="button" class="btn btn-outline-danger bi bi-google"> 로그인</a>
            <a href="/auth/login" role="button" class="btn btn-outline-dark bi bi-lock-fill"> 로그인</a>
            <a href="/auth/join" role="button" class="btn btn-outline-dark bi bi-person-circle"> 회원가입</a>
        </div>
    </div>
</nav>
</body>
</html>
```

### user/user-join.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<!--<head th:replace="~{layout/header :: head}">
    <title>회원가입</title>
</head>-->
<body>
<div th:replace="~{layout/header :: header}"></div>

<div id="posts_list">
    <div class="container col-md-4">
        <form th:action="@{/auth/joinProc}" method="post">
            <input type="hidden" name="_csrf" th:value="${_csrf.token}"/>
            <div class="form-group">
                <label>아이디</label>
                <input type="text" name="username" th:value="${userDto?.username}" class="form-control" placeholder="아이디를 입력해주세요"/>
                <span th:if="${valid_username}" th:text="${valid_username}" id="valid"></span>
            </div>

            <div class="form-group">
                <label>비밀번호</label>
                <input type="password" name="password" th:value="${userDto?.password}" class="form-control" placeholder="비밀번호를 입력해주세요"/>
                <span th:if="${valid_password}" th:text="${valid_password}" id="valid"></span>
            </div>

            <div class="form-group">
                <label>닉네임</label>
                <input type="text" name="nickname" th:value="${userDto?.nickname}" class="form-control" placeholder="닉네임을 입력해주세요"/>
                <span th:if="${valid_nickname}" th:text="${valid_nickname}" id="valid"></span>
            </div>

            <div class="form-group">
                <label>이메일</label>
                <input type="email" name="email" th:value="${userDto?.email}" class="form-control" placeholder="이메일을 입력해주세요"/>
                <span th:if="${valid_email}" th:text="${valid_email}" id="valid"></span>
            </div>

            <button type="submit" class="btn btn-primary bi bi-person"> 가입</button>
            <a href="/" role="button" class="btn btn-info bi bi-arrow-return-left"> 목록</a>
        </form>
    </div>
</div>

<div th:replace="~{layout/footer :: footer}"></div>
</body>
</html>

```

### user/user-login.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<!--<head th:replace="~{layout/header :: head}">
</head>-->
<body>
<div th:insert="~{layout/header :: header}"></div>

<div id="posts_list">
    <div class="container col-md-6">
        <form th:action="@{/auth/loginProc}" method="post">
            <input type="hidden" name="_csrf" th:value="${_csrf.token}"/>
            <div class="form-group">
                <label>아이디</label>
                <input type="text" class="form-control" name="username" placeholder="아이디를 입력해주세요">
            </div>

            <div class="form-group">
                <label>비밀번호</label>
                <input type="password" class="form-control" name="password" placeholder="비밀번호를 입력해주세요">
            </div>

            <span th:if="${error}">
                <p id="valid" class="alert alert-danger" th:text="${exception}"></p>
            </span>

            <button type="submit" class="form-control btn btn-primary bi bi-lock-fill"> 로그인</button>
        </form>
    </div>
</div>

<div th:replace="~{layout/footer :: footer}"></div>
</body>
</html>

```

---

[SecurityConfig.java](Spring%20Security%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%92%E1%85%AC%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%80%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%B8%20%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%201cb8b41398094b2da5fe7c9f5cc213ad/SecurityConfig%20java%20015f023cec014af9baee26055e10a93b.md)

### CustomUserDetailsService.java

```java
/**
 * 스프링 시큐리티가 로그인 요청을 가로채 로그인을 진행하고 완료 되면 UserDetails 타입의 오브젝트를
 * 스프링 시큐리티의 고유한 세션저장소에 저장 해준다.
 */
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    /* 유저의 권한 목록 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();

        collectors.add(() -> "ROLE_" + user.getRole());

        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /* 계정 만료 여부
     *  true : 만료 안됨
     *  false : 만료
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* 계정 잠김 여부
     *  true : 잠기지 않음
     *  false : 잠김
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* 비밀 번호 만료 여부
     *  true : 만료 안됨
     *  false : 만료
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* 사용자 활성화 여부
     *  true : 만료 안됨
     *  false : 만료
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
```

### **UserController**.java

```java
/**
 * 회원 관련 Controller
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    /*로그인 관련*/
    private final UserService userService;
		...
		
    @GetMapping("/auth/join")
    public String join() {
        return "/user/user-join";
    }

    /* 회원 가입 */
    @PostMapping("/auth/joinProc")
    public String joinProc(@Valid UserDto.Request dto, Errors errors, Model model) {
        log.info("==========joinProc 진입==========");
        if (errors.hasErrors()) {
            /* 회원가입 실패 시 입력 데이터 값을 유지 */
            model.addAttribute("userDto", dto);

            log.error("Error = "+errors);

            /* 유효성 통과 못한 필드와 메세지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            /* 회원가입 페이지로 다시 리턴 */
            return "/user/user-join";
        }
        userService.userJoin(dto);
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/user/user-login";
    }

   ...
}

```

### **UserService**.java

```java
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    /* 회원 가입 */
    @Transactional
    public void userJoin(UserDto.Request dto) {
        dto.setPassword(encoder.encode(dto.getPassword()));
        userRepository.save(dto.toEntity());
    }

 		...
}
```

---