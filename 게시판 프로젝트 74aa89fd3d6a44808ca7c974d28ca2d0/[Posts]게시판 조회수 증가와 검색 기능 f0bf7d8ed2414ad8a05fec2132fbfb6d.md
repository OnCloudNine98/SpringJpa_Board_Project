# [Posts]게시판 조회수 증가와 검색 기능

### 구현 화면

![게시물 검색 결과 화면](%5BPosts%5D%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%8C%E1%85%A9%E1%84%92%E1%85%AC%E1%84%89%E1%85%AE%20%E1%84%8C%E1%85%B3%E1%86%BC%E1%84%80%E1%85%A1%E1%84%8B%E1%85%AA%20%E1%84%80%E1%85%A5%E1%86%B7%E1%84%89%E1%85%A2%E1%86%A8%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%20f0bf7d8ed2414ad8a05fec2132fbfb6d/Untitled.png)

게시물 검색 결과 화면

![게시글 클릭후 상세 게시글 조회 화면](%5BPosts%5D%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%8C%E1%85%A9%E1%84%92%E1%85%AC%E1%84%89%E1%85%AE%20%E1%84%8C%E1%85%B3%E1%86%BC%E1%84%80%E1%85%A1%E1%84%8B%E1%85%AA%20%E1%84%80%E1%85%A5%E1%86%B7%E1%84%89%E1%85%A2%E1%86%A8%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%20f0bf7d8ed2414ad8a05fec2132fbfb6d/Untitled%201.png)

게시글 클릭후 상세 게시글 조회 화면

![다시 돌아가면 조회수가 늘어난 모습](%5BPosts%5D%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%8C%E1%85%A9%E1%84%92%E1%85%AC%E1%84%89%E1%85%AE%20%E1%84%8C%E1%85%B3%E1%86%BC%E1%84%80%E1%85%A1%E1%84%8B%E1%85%AA%20%E1%84%80%E1%85%A5%E1%86%B7%E1%84%89%E1%85%A2%E1%86%A8%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%20f0bf7d8ed2414ad8a05fec2132fbfb6d/Untitled%202.png)

다시 돌아가면 조회수가 늘어난 모습

### **접근 로직**

1. 게시글 목록 화면(index.html )에서 
검색 칸에 제목을 입력하고 검색버튼을 누르면 PostsIndexController 를 통해 검색한 결과의 게시글 목록을 보여줌 
2. 조회수 관련해서는 게시글 목록의 특정 게시글을 클릭하면 PostsIndexController 를 통해 상세보기 정보를 경로 변수 id를 통해 찾아와 화면에 출력해 주면서 `postsService.updateView(id);` 
를 통해 조회수를 올려준다.

### posts/posts-search.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<div th:replace="~{layout/header :: header}"></div>

<div id="posts_list">
    <table id="table" class="table table-horizontal">
        <thead id="thead">
        <tr>
            <th>번호</th>
            <th class="col-md-6 text-center">제목</th>
            <th>작성자</th>
            <th>작성일</th>
            <th>조회수</th>
        </tr>
        </thead>
        <tbody id="tbody">
        <tr th:each="post : ${searchList}">
            <td th:text="${post.id}"></td>
            <td><a th:href="@{/posts/read/{id}(id=${post.id})}" th:text="${post.title}"></a></td>
            <td th:text="${post.writer}"></td>
            <td th:text="${post.modifiedDate}"></td>
            <td th:text="${post.view}"></td>
        </tr>
        </tbody>
    </table>
    <div class="text-right">
        <a href="/posts/write" role="button" class="btn btn-primary bi bi-pencil-fill"> 글쓰기</a>
    </div>

    <!-- Search Paging -->
    <div class="pagination justify-content-center">
        <div th:if="${hasPrev}">
            <a th:href="@{/posts/search(keyword=${keyword}, page=${previous})}" role="button" class="btn btn-lg bi bi-caret-left-square-fill"></a>
        </div>
        <div th:unless="${hasPrev}">
            <a href="/posts/search?keyword=${keyword}&amp;page=${previous}" role="button" class="btn btn-lg bi bi-caret-left-square-fill disabled"></a>
        </div>

        <div th:if="${hasNext}">
            <a th:href="@{/posts/search(keyword=${keyword}, page=${next})}" role="button" class="btn btn-lg bi bi-caret-right-square-fill"></a>
        </div>
        <div th:unless="${hasNext}">
            <a href="/posts/search?keyword=${keyword}&amp;page=${next}" role="button" class="btn btn-lg bi bi-caret-right-square-fill disabled"></a>
        </div>
    </div>

</div>
<!-- 푸터 포함 -->
<div th:replace="~{layout/footer :: footer}"></div>
</html>
```

---

### PostsIndexController.java

게시글 관련 화면 연결 컨트롤러

```java
@Slf4j
@RequiredArgsConstructor
@Controller
public class PostsIndexController {
    private final PostsService postsService;

  

    /* 글 상세보기 */
    @GetMapping("/posts/read/{id}")
    public String read(@PathVariable("id") Long id, @LoginUser UserDto.Response user, Model model) {
        PostsDto.Response dto = postsService.findById(id);
        List<CommentDto.Response> comments = dto.getComments();

        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }
        /* 사용자 관련 */
        if (user != null) {
            model.addAttribute("user", user);

            /* 게시글 작성자 본인인지 확인 */
            if (dto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }

            /* 댓글 작성자 본인인지 확인 */
            if (comments.stream().anyMatch(s -> s.getUserId().equals(user.getId()))) {
                model.addAttribute("isWriter", true);

            }
        }

        postsService.updateView(id); // views ++
        model.addAttribute("posts", dto);

        /* 현재 세션의 Userid 전달 _ 댓글 삭제 */
        model.addAttribute("sessionId", user.getId());
        return "posts/posts-read";
    }
    
    /*게시물 검색*/
    @GetMapping("/posts/search")
    public String search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                         Pageable pageable, @LoginUser UserDto.Response user) {
        Page<Posts> searchList = postsService.search(keyword, pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", searchList.hasNext());
        model.addAttribute("hasPrev", searchList.hasPrevious());

        return "posts/posts-search";
    }
}
```

- 

---

### PostsService.java

```java
@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    /* Views Counting */
    @Transactional
    public int updateView(Long id) {
        return postsRepository.updateView(id);
    }

    /* Sort */
    @Transactional(readOnly = true)
    public Page<Posts> search(String keyword, Pageable pageable) {

        Page<Posts> postsList = postsRepository.findByTitleContaining(keyword, pageable);
        return postsList;
    }
    ...
 }
```

> [바로 앞서](%5BPosts%5D%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%8C%E1%85%A9%E1%84%92%E1%85%AC%E1%84%89%E1%85%AE%20%E1%84%8C%E1%85%B3%E1%86%BC%E1%84%80%E1%85%A1%E1%84%8B%E1%85%AA%20%E1%84%80%E1%85%A5%E1%86%B7%E1%84%89%E1%85%A2%E1%86%A8%20%E1%84%80%E1%85%B5%E1%84%82%E1%85%B3%E1%86%BC%20f0bf7d8ed2414ad8a05fec2132fbfb6d.md) 말했듯이 `@Transactional` 의 클래스에서  Repository 메서드 호출
> 

### PostsRepository.java

```java
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Modifying
    @Query("update Posts p set p.view = p.view + 1 where p.id = :id")
    int updateView(@Param("id")Long id);

    Page<Posts> findByTitleContaining(String keyword, Pageable pageable);
}
```

- `updateView`
    - `@Modifying`
    : Spring Data JPA 에서 data의 상태를 변경 할 때 주로 사용되며
    주로 `UPDATE`, `DELETE`, `INSERT`와 같은 데이터베이스 조작 쿼리에 사용 돼 JPA의 표준
    `@Query` annotation 과 같이 사용된다.
        
        <aside>
        💡 `@Modifying` 는 변경된 엔티티를 자동으로 감지하여 데이터베이스에 반영하는 JPA 와 달리 명시적으로DB 조작 쿼리를 실행하고 DB를 변경하는 작업이다 보니 주로 `@Transactional` 와 사용되는 경우가 많다.
        
        </aside>
        
    - `@Query`
    : JPQL(Java Persistence Query Language) 또는 네이티브 SQL 쿼리를 정의하는 데 사용되며 이를 통해 복잡한 쿼리를 직접 작성 할 수 있습니다.
        
        > updateView 의 파라미터 `@Param("id")Long id` 는 쿼리문 `:id` 의 binding parameter
        > 

---