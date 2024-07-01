# 구현 중 Error 사항

- **application-auth.properties 관련**
    
    처음 완성된 코드를 실행 했을때는 로그창에 다음과 같은 오류가 발생 하였다
    
    ```prolog
    Description:
    
    Method springSecurityFilterChain in org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration required a bean of type 'org.springframework.security.oauth2.client.registration.ClientRegistrationRepository' that could not be found.
    
    Action:
    
    Consider defining a bean of type 'org.springframework.security.oauth2.client.registration.ClientRegistrationRepository' in your configuration.
    ```
    
    원인을 알아본 결과 프로젝트에 사용한 Sping Security / Auth 와 관련 된 문제로 
    `application-auth.properties` 로 따로 설정을 안해줘서 발생한 문제 였다.
    
- **SecurityConfig.java 관련**
    
    기존 [SecurityConfig.java](http://SecurityConfig.java) 의 버전 업으로 코드를 수정해주는 과정에서
    
    ![Untitled](%E1%84%80%E1%85%AE%E1%84%92%E1%85%A7%E1%86%AB%20%E1%84%8C%E1%85%AE%E1%86%BC%20Error%20%E1%84%89%E1%85%A1%E1%84%92%E1%85%A1%E1%86%BC%2041fd87affcd24e00883efe211d78af83/Untitled.png)
    
    ![Untitled](%E1%84%80%E1%85%AE%E1%84%92%E1%85%A7%E1%86%AB%20%E1%84%8C%E1%85%AE%E1%86%BC%20Error%20%E1%84%89%E1%85%A1%E1%84%92%E1%85%A1%E1%86%BC%2041fd87affcd24e00883efe211d78af83/Untitled%201.png)
    
    두 메서드를 `SecurityFilterChain filterChain` 로 바꿔주는 과정에서 막연한 생각으로 두 메서드 모두 finterChain 으로 수정해주려다가 오버 로딩으로 접근하는 실수를 하여
    
    ```prolog
    org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration': 
    Unsatisfied dependency expressed through method 'setFilterChains' parameter 0: Error creating bean with name 'filterChains' defined in class path resource [practice/copy/infrastructure/config/SecurityConfig.class]: 
    Failed to instantiate [org.springframework.security.web.SecurityFilterChain]: Factory method 'filterChains' threw exception with message: Cannot apply org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer@105cca28 to already built object
    
    ```
    
    의 오류를 발생 시킴 ⇒  위의 메서드를 다른 방식으로 수정해줘 문제 해결
    
- **스프링부트 mustache 한글 깨짐**
    
    특정 스프링부트 버전과 mustache 조합에서는 한글이 ???
    로 깨지는 현상이 발생한다. 이에 대해 찾아보니
    ⇒ 스프링 부트 버전을 down해주거나 / 
    application.properties 에 다음 의 설정 추가해주기
    
    ```prolog
    server.servlet.encoding.force-response=true
    ```
    
- **CSRF 관련 문제**
    
    csrf 와 관련해서 아래와 같은 문제 발생하였다.
    
    ```prolog
    com.samskivert.mustache.MustacheException$Context: No method or field with name '_csrf' on line 5
    ```
    
    - 첫번째 접근
        
        인터넷에서 찾아본 결과 이 에러와 관련한 직접적인 내용은 잘 나오지 않았고 결국 여러가지 시도 끝에 형식의 mustache **형식의** 문제로 단정짓고 접근 하였다.
        
        ```html
        <input type="hidden" name="_csrf" value="{{_csrf.token}}"/>
        ```
        
        ```html
        <input type="hidden" name="_csrf" value="{{#_csrf}}token{{/_csrf}}"/>
        ```
        
        > **[수정 결과]**
        하지만 이방법을 사용하니 value 값을 인식하지 못하는 문제가 발생하였고 다시
        옳지 못한 접근 임을 확인하고 롤백 하였다.
        > 
    - 두번째 접근
        
        에러 문구와 관련하여 아무리 찾아봐도 직접적인 해결책은 보이지 않았고 결국 고민하다
        csrf토큰 관련 Mustache 의 문제라고 판단. 하지만 인터넷에는 Mustache 와 관련한 csrf에 관한 내용이 많지 않아 어차피 추후에 할 [Mustache → Thymeleaf 변환](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134.md)을 먼저 하였고 결과적으로 Error 를 해결
        
    
- **Thymeleaf 관련 문제**
    
    csrf 와 관련해서 아래와 같은 문제 발생하였다.
    
    ```prolog
    WARN 7008 --- [nio-8080-exec-1] actStandardFragmentInsertionTagProcessor : [THYMELEAF][http-nio-8080-exec-1][index] 
    Deprecated unwrapped fragment expression "layout/header :: header" found in template index, line 6, col 6. 
    Please use the complete syntax of fragment expressions instead ("~{layout/header :: header}"). The old, unwrapped syntax for fragment expressions will be removed in future versions of Thymeleaf.
    ```
    
    Thymeleaf 버전에 관련해 표현 형식과 관련된 오류로 
    
    **`<th:block th:include="footer :: index_footer"/>`** 를 
    
    **`<th:block th:replace="~{footer :: index_footer}"/>`** 로 수정 해주면 된다.
    
- **로그인 관련 문제**
    
    DB에도 올바르게 들어간 아이디, 비밀번호 data로 로그인 하였더니 
    다음과 같은 문제 발생였고
    
    - 문제 발생 화면/ 코드
        
        ![Untitled](%E1%84%80%E1%85%AE%E1%84%92%E1%85%A7%E1%86%AB%20%E1%84%8C%E1%85%AE%E1%86%BC%20Error%20%E1%84%89%E1%85%A1%E1%84%92%E1%85%A1%E1%86%BC%2041fd87affcd24e00883efe211d78af83/Untitled%202.png)
        
        ![Untitled](%E1%84%80%E1%85%AE%E1%84%92%E1%85%A7%E1%86%AB%20%E1%84%8C%E1%85%AE%E1%86%BC%20Error%20%E1%84%89%E1%85%A1%E1%84%92%E1%85%A1%E1%86%BC%2041fd87affcd24e00883efe211d78af83/Untitled%203.png)
        
    
    콘솔에 어떠한 Error 도 뜨지 않아 log 를 찍었더니 다음의 메세지가 나왔다.
    
    ```prolog
    ERROR 5976 --- [nio-8080-exec-4] p.c.a.s.auth.CustomAuthFailureHandler    : User account has expire
    ```
    
    `User account has expire` 라는 에러는 찾아보니 springsecurity 의
    AccountExpiredException 예외와 관련이 있었고 이와 연관된 프로젝트내 파일을 살펴보니 다음과 같이 설정 돼 있었다
    
    ![CustomUserDetails.java](%E1%84%80%E1%85%AE%E1%84%92%E1%85%A7%E1%86%AB%20%E1%84%8C%E1%85%AE%E1%86%BC%20Error%20%E1%84%89%E1%85%A1%E1%84%92%E1%85%A1%E1%86%BC%2041fd87affcd24e00883efe211d78af83/Untitled%204.png)
    
    CustomUserDetails.java
    
    이값을 `return true;` 로 수정하니 로그인문제가 해결 되었다.
    
- **스프링 부트3.2 파라미터 이름 인식 관련**
    
    핵심은 스프링 부트 3.2부터 자바 컴파일러에 -parameters 옵션을 넣어주어야 애노테이션의 이름을 생략있음
    
    ```prolog
    ERROR 12316 --- [nio-8080-exec-4] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: java.lang.IllegalArgumentException: Name for argument of type [java.lang.Long] not specified, and parameter name information not available via reflection. Ensure that the compiler uses the '-parameters' flag.] with root cause
    ```
    
    <aside>
    💡 문제 원인(상세)
    자바를 컴파일할 때 매개변수 이름을 읽을 수 있도록 남겨두어야 사용할 수 있다. 컴파일 시점에 `-parameters` 옵션을 사용하면 매개변수 이름을 사용할 수 있게 남겨두는데 스프링 부트 3.2 전까지는 바이트코드를 파싱해서 매개변수 이름을 추론하려고 시도 하였지만 스프링 부트 3.2 부터는 이런 시도를 하지 않는다.
    
    </aside>
    
    - 해결방안 3가지
        
        **해결 방안1**
        
        애노테이션에 이름을 생략하지 않고 다음과 같이 항상 적어준다.
        
        `@RequestParam("username") String username`
        
        `@PathVariable("userId") String userId`
        
        `@Qualifier("memberRepository") MemberRepository memberRepository`
        
        **해결 방안2**
        
        컴파일 시점에 -parameters 옵션 적용
        
        1. IntelliJ IDEA에서 File -> Settings를 연다. (Mac은 IntelliJ IDEA -> Settings)
        
        2. Build, Execution, Deployment → Compiler → Java Compiler로 이동한다.
        
        3. Additional command line parameters라는 항목에 다음을 추가한다.
        
        **`-parameters`**
        
        4. out 폴더를 삭제하고 다시 실행한다. 꼭 out 폴더를 삭제해야 다시 컴파일이 일어난다.
        
        **해결 방안3(권장)**
        
        Gradle을 사용해서 빌드하고 실행한다.
        
        참고로 이 문제는 Build, Execution, Deployment -> Build Tools -> Gradle에서
        
        Build and run using를 IntelliJ IDEA로 선택한 경우에만 발생한다. Gradle로 선택한 경우에는 Gradle이 컴파일 시점에 해당 옵션을 자동으로 적용해준다.
        
        > **해결방안 3번을 선택하시면 코드 변경없이 문제를 해결할 수 있어 3번을 권장**
        >