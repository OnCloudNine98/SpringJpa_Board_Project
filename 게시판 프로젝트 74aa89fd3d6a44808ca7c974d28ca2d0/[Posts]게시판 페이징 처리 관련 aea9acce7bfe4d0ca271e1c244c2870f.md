# [Posts]게시판 페이징 처리 관련

### 구현 화면

페이지당 최대 11개의 게시글을 나타내고 이동 버튼으로 다음/이전 페이지 표현

![최대 11개의 게시글을 한 페이지에 표현한 화면](%5BPosts%5D%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%8C%E1%85%B5%E1%86%BC%20%E1%84%8E%E1%85%A5%E1%84%85%E1%85%B5%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB%20aea9acce7bfe4d0ca271e1c244c2870f/Untitled.png)

최대 11개의 게시글을 한 페이지에 표현한 화면

![다음 페이지의 버튼으로 표현한 다음 페이지의 게시글 화면](%5BPosts%5D%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%8C%E1%85%B5%E1%86%BC%20%E1%84%8E%E1%85%A5%E1%84%85%E1%85%B5%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB%20aea9acce7bfe4d0ca271e1c244c2870f/Untitled%201.png)

다음 페이지의 버튼으로 표현한 다음 페이지의 게시글 화면

### **접근 로직**

1. 루트 URL 로 접근을 하면 PostsIndexController 의 index 메서드를 통해 메인 화면 접근
2. 에노테이션을 통해 페이징 처리 접근 

### posts/posts-page.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<div th:fragment="posts-page" class="pagination justify-content-center">
    <div th:if="${hasPrev}">
        <a th:href="'?page=' + ${previous}" role="button" class="btn btn-lg bi bi-caret-left-square-fill"></a>
    </div>
    <div th:unless="${hasPrev}">
        <a href="?page=${previous}" role="button" class="btn btn-lg bi bi-caret-left-square-fill disabled"></a>
    </div>

    <div th:if="${hasNext}">
        <a th:href="'?page=' + ${next}" role="button" class="btn btn-lg bi bi-caret-right-square-fill"></a>
    </div>
    <div th:unless="${hasNext}">
        <a href="?page=${next}" role="button" class="btn btn-lg bi bi-caret-right-square-fill disabled"></a>
    </div>
</div>
</html>

```

- Thymeleaf 의 조건 문을 이용하여 이전/다음 페이지가 없으면 `disabled` 해주도록 설적

---

### PostsIndexController.java

게시글 관련 화면 연결 컨트롤러

```java
@Slf4j
@RequiredArgsConstructor
@Controller
public class PostsIndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                        Pageable pageable, @LoginUser UserDto.Response user) {
        Page<Posts> list = postsService.pageList(pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        return "index";
    }

  
}
```

- `index`
    - `@PageableDefault`
    : Paging과  관련하여 기본값을 설정하는 Annotation
        - **size** : 한 페이지에 담을 모델의 수
        - **sort :** 정렬의 기준이 되는 속성
        - **direction :** 오름차순 / 내림차순 중 기준
        - **Pageable pageable** : PageableDefault 값을 갖고 있는 변수
        
        > Annotation을 사용하지 않으면 직접 페이징 처리를 구현 하고 
        Repository 에 정렬 Query 를 작성 해야 한다.
        > 
    - `Page` 인터페이스
    :  Spring Data JPA에서 페이징 기능을 지원하기 위해 사용하는 인터페이스
     ( `int getPageNumber()` , `int getPageSize()` , `long getOffset()` 등의 메서드 존재 )
        
        <aside>
        💡 **Page인터페이스**로 타입을 지정할 때 주의할 점
        페이징 정보를 처리하기 위해 주로 `Pageable` 을 파라미터로 받아야 한다.
        
        </aside>
        
        <aside>
        💡 Spring Data JPA 의 페이지 처리할 때 주의할 점
        Spring Data JPA의 페이지 값은 `0` 부터 시작 하여 이점을 유의 하자.
        
        </aside>
        

---

### PostsService.java

```java
@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {
  private final PostsRepository postsRepository;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public Page<Posts> pageList(Pageable pageable) {
        return postsRepository.findAll(pageable);
  }

    ...
 }
```

> 이때 postsRepository 의 `findAll()` 는 상속받은 JpaRepository 의 `findAll()`
> 

---