# OAuth 2.0 를 이용한 로그인 기능[네이버]

### 구현 화면

![Untitled](OAuth%202%200%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%5B%E1%84%80%E1%85%AE%E1%84%80%E1%85%B3%E1%86%AF%5D%20739067e99ccf49a5810ce99b53c6f16e/Untitled.png)

아이디와 비밀번호가 일치하면 로그인 요청 코드가 담긴 콜백 url값을 보내고 
그것을 oauth2.0/token이 전달받아 인증

![Untitled](OAuth%202%200%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%5B%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%87%E1%85%A5%5D%202a69edee8f4d478db50f0aafcbe7cb35/Untitled.png)

## 실제 서비스 등록

1-1 ) 네이버 OAuth 서비스를 등록하기 위해 네이버 개발자 센터 이동

[https://developers.naver.com/main/](https://developers.naver.com/main/)

1-2 ) Application > 애플리케이션 등록 > 상단 중앙의 프로젝트 선택 > 애플리케이션 이름/사용API 선택

![Untitled](OAuth%202%200%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%5B%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%87%E1%85%A5%5D%202a69edee8f4d478db50f0aafcbe7cb35/Untitled%201.png)

1-3 ) 환경 추가 (PC 웹) > 서비스 URL (http://localhost:8080) > 네이버 로그인 Callback URL(http://localhost:8080/login/oauth2/naver) > 등록하기 버튼

![Untitled](OAuth%202%200%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%5B%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%87%E1%85%A5%5D%202a69edee8f4d478db50f0aafcbe7cb35/Untitled%202.png)

1-4 ) Client ID / Client Secret 정보 획득

![Untitled](OAuth%202%200%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%5B%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%87%E1%85%A5%5D%202a69edee8f4d478db50f0aafcbe7cb35/Untitled%203.png)

---

## header.html

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

## resources/**application-oauth.properties**

```
# Naver
spring.security.oauth2.client.registration.naver.client-id=m2lkf4BBa0Plrzp5A25Q
spring.security.oauth2.client.registration.naver.client-secret=hePuqqsNFMt

# Naver Spring Security 수동 입력
# == http://localhost:8080/login/oauth2/code/naver
spring.security.oauth2.client.registration.naver.redirect-uri={baseUrl}/{action}/oauth2/code/{registrationId}
spring.security.oauth2.client.registration.naver.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.naver.scope=name,email,nickname
spring.security.oauth2.client.registration.naver.client-name=Naver
# provider
spring.security.oauth2.client.provider.naver.authorization-uri=https://nid.naver.com/oauth2.0/authorize
spring.security.oauth2.client.provider.naver.token-uri=https://nid.naver.com/oauth2.0/token
spring.security.oauth2.client.provider.naver.user-info-uri=https://openapi.naver.com/v1/nid/me
# Security의 기준이 되는 username의 이름을 네이버에서는 response로 (회원 조회시 반환되는 JSON 형태 때문)
spring.security.oauth2.client.provider.naver.user-name-attribute=response
```

<aside>
💡 네이버는 Srping Security 를 공식 지원하지 않기 때문에 Provider를 직접 입력.

</aside>

```
# OAUTH
spring.profiles.include=oauth
```

> [application-XXX.properties](http://application-XXX.properties) 로 만들면 XXX라는 profile이 생성 되어 관리 할 수 있고 해당 정보를 가져오기 위해 [application.properties](http://application.properties) 의 `spring.profiles.include` 에 정보를 입력
> 

---

### **OAuthAttributes.java**

```java
/**
 * OAuth DTO class
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String username;
    private String nickname; 
    private String email;
    private Role role;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        /* 구글인지 네이버인지 카카오인지 구분하기 위한 메소드 (ofNaver, ofKaKao) */
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        //사실상 if ("google".equals(registrationId)) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .username((String) attributes.get("email"))
                .email((String)attributes.get("email"))
                .nickname((String) attributes.get("name"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        /* JSON형태이기 때문에 Map을 통해 데이터를 가져옴. */
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        log.info("naver response : " + response);

        return OAuthAttributes.builder()
                .username((String) attributes.get("email"))
                .email((String) attributes.get("email"))
                .nickname((String) attributes.get("name"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .nickname(nickname)
                .role(Role.SOCIAL)
                .build();
    }
}

```

---

### **로그인 기능 구현 중 발생한 Error 사항**

1. 기능을 구현 하고 로그인 화면 버튼을 클릭하니 
    
    ![Untitled](OAuth%202%200%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%5B%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%87%E1%85%A5%5D%202a69edee8f4d478db50f0aafcbe7cb35/Untitled%204.png)
    
    다음과 같은 오류 발생
    
    ![Untitled](OAuth%202%200%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%5B%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%87%E1%85%A5%5D%202a69edee8f4d478db50f0aafcbe7cb35/Untitled%205.png)
    
2. 오류를 해결 하기 위해 
네이버 고객센터 문의글([https://help.naver.com/service/23029/contents/19600?lang=ko](https://help.naver.com/service/23029/contents/19600?lang=ko))
의 내용을 참고 ⇒  애플리케이션의 **Callback URL**에 문제가 있다고 판단
3. 문제를 해결 하기 위해 네이버 개발자 센터 애플리케이션에 등록되어 있는 **Callback URL**과 서비스에서 네이버로 인증 요청 시 전달하는 API 파라미터(redirect_uri) 값과 정확히 일치하도록 설계( 참고로 Callback URL은 서비스 URL과는 다르게 서브 도메인을 포함한 모든 URL 요소를 정확히 비교)
    
    > 쉽게 말해 ****자신이 운영하는 사이트의 **Callback URL** 을 네이버 개발자 센터 애플리케이션의 **Callback URL**로 입력
    > 
    

[**자신이 운영하는 사이트의 Callback URL을 모르는 경우**](OAuth%202%200%20%E1%84%85%E1%85%B3%E1%86%AF%20%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%AD%E1%86%BC%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%5B%E1%84%82%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%87%E1%85%A5%5D%202a69edee8f4d478db50f0aafcbe7cb35/%E1%84%8C%E1%85%A1%E1%84%89%E1%85%B5%E1%86%AB%E1%84%8B%E1%85%B5%20%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%BC%E1%84%92%E1%85%A1%E1%84%82%E1%85%B3%E1%86%AB%20%E1%84%89%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%90%E1%85%B3%E1%84%8B%E1%85%B4%20Callback%20URL%E1%84%8B%E1%85%B3%E1%86%AF%20%E1%84%86%E1%85%A9%E1%84%85%E1%85%B3%E1%84%82%202ac3b6fcddcc44beb7ed24d58db27ac7.md)