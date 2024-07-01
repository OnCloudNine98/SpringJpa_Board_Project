# Mustache → Thymeleaf 변환

**Mustache 에서 Thymeleaf 로 변환하는 이유**

최근 Spring 업계에서는 Thymeleaf 가 증가하는 추세 라는 사실을 현직자(강사님)를 통해 들었고
 Mustache 에 비해 더 표현력이 좋아 사용과 응용에 용이하여 Thymeleaf 로 변환 하였습니다.

### **변환 내용**

- **Mustache 의 조건문 Section ⇒ Thymeleaf 의 조건문 속성**
    - `{{#user}}` **⇒** `<div th:if="${user}">`
    - **Mustache** 에서는 Section 을 통해 데이터의 존재 여부나 특정 블록을 렌더링
    - **Thymeleaf** 에서는 속성을 통해 특정 조건에 따라 해당 HTML요소를 렌더링할지 여부 결정
- **Mustache 와 Thymeleaf 의 Text Rendering 차이**
    - `{{variable}}` **⇒** `<th:text="${name}">`
    - **Mustache** 는 주로 Section 사이의 내용이 템플릿에 삽입될때 사용 되며
    `{{variable}}` 는 HTML ESCAPE된 텍스트,`{{{variable}}}` 는 escape 되지 않은 텍스트 출력
    - **Thymeleaf** HTML 태그의 텍스트 콘텐츠를 설정하는 데 사용되며 변수 값을 텍스트로 렌더링
    하고 기본적으로 HTML ESCAPE 가 적용.
- **Mustache 의 변수 삽입 ⇒ Thymeleaf 의 링크 표현식**
    - `<a href="{{url}}">Link</a>` **⇒** `<a th:href="@{${url}}">Link</a>`
    
    `<a href="/~/~?keyword={{keyword}}` **⇒** `<a th:href="@{/~/~(keyword=${keyword}) }`
    - **Mustache** 는 단순히 url 변수를 삽입 하는 접근의 표현식
    - **Thymeleaf** 는 링크 표현식을 통해 링크를 동적으로 설정
- **Mustache 의 변수 삽입(Interpolation) ⇒ Thymeleaf 의 변수 표현식(Expression)**
    - `{{posts.writer}}` ⇒ `[[${posts.writer}]]`
    - **Mustache** 는 단순히 변수를 대체 하여 클라이언트/서버 모두에서 사용.
    - **Thymeleaf**  는 서버사이드에서 표현식을 실행하고 그 결과 값을 HTML로 변환.
- **Mustache 의 Partial Template ⇒ Thymeleaf 의 Attribute Replacement**
    - `{{>comment/list}}` ⇒ `<div th:replace="~{comment/list :: list}"></div>`
    - **Mustache** 는 부분 템플릿을 렌더링
    - **Thymeleaf**  는 주어진 속성을 다른 템플릿의 내용으로 대체

---

## 변환중 오류 상황

- Fragment 관련
    - 문제 화면
        
        ![의도된 화면](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134/Untitled.png)
        
        의도된 화면
        
        ![의도된 화면의 소스 코드](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134/Untitled%201.png)
        
        의도된 화면의 소스 코드
        
        ![실제 화면](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134/Untitled%202.png)
        
        실제 화면
        
    
    결국 화면의 표면적인 차이는 Bootstrap이 적용 되냐 안되냐의 차이 였으며
    좀더 본질적으로 접근하면 ThymeLeaf 의 Fragment 개념적용의 문제 였다.
    
- Ajax 관련
    
    기존 mustache는 템플릿 코드에서 `<button>` 요소에 `action` 태그가 없더라도, 버튼을 눌렀을 때 페이지가 이동하거나 특정 동작이 수행하는 반면
    Thymeleaf 는 `<button>` 요소에 `action` 태그없으면 직접 JavaScript 이벤트 핸들러를 통해 전송해야 한다.
    
    - 문제 화면
        
        ![의도된 화면](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134/Untitled.png)
        
        의도된 화면
        
        ![의도된 화면의 소스 코드](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134/Untitled%201.png)
        
        의도된 화면의 소스 코드
        
        ![실제 화면](Mustache%20%E2%86%92%20Thymeleaf%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB%206906effeaa2544aab4bdebd4dca20134/Untitled%202.png)
        
        실제 화면
        
    
    화면의 표면적인 차이는 Bootstrap이 적용 되냐 안되냐의 차이 였다.
    이는 좀더 본질적으로 접근하면 ThymeLeaf 의 Fragment 개념적용의 문제 였다.
    
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