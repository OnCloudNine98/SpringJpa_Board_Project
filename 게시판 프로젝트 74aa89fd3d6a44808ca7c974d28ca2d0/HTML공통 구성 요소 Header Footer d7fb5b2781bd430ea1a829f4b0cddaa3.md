# HTML공통 구성 요소 Header/Footer

각 화면의 중복되는 Layout을 뿐 아니라 중복되는 기능적 코드를 따로 분리 시키는 역할
 

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

- 아래의 화면을 나타내는 구성요소를 따로 분리시켜 나타 낸다.
    
    ![Untitled](HTML%E1%84%80%E1%85%A9%E1%86%BC%E1%84%90%E1%85%A9%E1%86%BC%20%E1%84%80%E1%85%AE%E1%84%89%E1%85%A5%E1%86%BC%20%E1%84%8B%E1%85%AD%E1%84%89%E1%85%A9%20Header%20Footer%20d7fb5b2781bd430ea1a829f4b0cddaa3/Untitled.png)
    
- `<html xmlns:th="http://www.thymeleaf.org" th:fragment="header">`
다른 html 에서 사용하기 위해 Tmeplate Fragment 를 이용.

---

### layout/footer.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      th:fragment="footer">
<body>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="/js/app.js"></script> <!--ThmeLeaf 관련 JavaScript(Ajax 이용 목적)-->

    <footer  id="footer" > <!--th:fragment="footer"-->
        Powered by LSM
        <a href="https://github.com/" target="_blank" class="bi bi-github"></a>
        <i class="bi bi-reception-4"></i>
        <i class="bi bi-phone"> 02-1234-5678</i>
    </footer>

</body>

</html>
```

- 아래의 화면을 나타내는 구성요소를 따로 분리시켜 나타 낸다.
    
    ![Untitled](HTML%E1%84%80%E1%85%A9%E1%86%BC%E1%84%90%E1%85%A9%E1%86%BC%20%E1%84%80%E1%85%AE%E1%84%89%E1%85%A5%E1%86%BC%20%E1%84%8B%E1%85%AD%E1%84%89%E1%85%A9%20Header%20Footer%20d7fb5b2781bd430ea1a829f4b0cddaa3/Untitled%201.png)
    

- `<script src="/js/app.js"></script>`
보통 click event 관한 method 를 `<script>` 태그 안에 넣어 설정해줘야 되지만
프로젝트 에서는 event method 들을 한데 모아 [`app.js` 파일](JavaScript%20%E1%84%8B%E1%85%AA%20%E1%84%8B%E1%85%B2%E1%84%92%E1%85%AD%E1%84%89%E1%85%A5%E1%86%BC%20%E1%84%80%E1%85%A5%E1%86%B7%E1%84%89%E1%85%A1%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB(app%20js)%20a7aa307c3044477393d4dd2cf4e80303.md)에 독립 시켜 줬고 
이를 `<script>` 태그의 `src` 를 통해 접근 하였다.

---