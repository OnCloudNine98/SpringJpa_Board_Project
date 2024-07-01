# [Comment]댓글 수정및 삭제 + 수정/삭제 제한 기능

### 구현 화면

로그인을 성공 한뒤 게시글을 작성하여 정보와 함께 저장

![Untitled](%5BComment%5D%E1%84%83%E1%85%A2%E1%86%BA%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20+%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20%E1%84%8C%E1%85%A6%E1%84%92%E1%85%A1%E1%86%AB%2021f0668fb2c44907beadcfa9d6f4bbc4/Untitled.png)

![Untitled](%5BComment%5D%E1%84%83%E1%85%A2%E1%86%BA%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20+%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20%E1%84%8C%E1%85%A6%E1%84%92%E1%85%A1%E1%86%AB%2021f0668fb2c44907beadcfa9d6f4bbc4/Untitled%201.png)

![Untitled](%5BComment%5D%E1%84%83%E1%85%A2%E1%86%BA%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20+%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20%E1%84%8C%E1%85%A6%E1%84%92%E1%85%A1%E1%86%AB%2021f0668fb2c44907beadcfa9d6f4bbc4/Untitled%202.png)

![Untitled](%5BComment%5D%E1%84%83%E1%85%A2%E1%86%BA%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20+%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20%E1%84%8C%E1%85%A6%E1%84%92%E1%85%A1%E1%86%AB%2021f0668fb2c44907beadcfa9d6f4bbc4/Untitled%203.png)

![Untitled](%5BComment%5D%E1%84%83%E1%85%A2%E1%86%BA%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20+%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20%E1%84%8C%E1%85%A6%E1%84%92%E1%85%A1%E1%86%AB%2021f0668fb2c44907beadcfa9d6f4bbc4/Untitled%204.png)

### **로직**

> 수정과 삭제는 처음 게시글 상세 페이지(posts/posts-read.html) 의 
**템플릿 조각**으로 존재하는 댓글 리스트 페이지(comment/list.html) 를 띄울 때
게시글 상세 보기 Controller 에서 model attribute 을 통해 넘겨준 작성자 정보(`”writer”`)
에 따라 수정/삭제 버튼이 보이도록 타임리프 `th:if="${isWriter}"` 를 이용하여 접근
> 

**수정**

1. 댓글 조회 로직을 통해 띄어준 댓글 목록 리스트의 **연필 표시 버튼(댓글 수정 버튼)** 클릭
2. Bootstrap 의 `collapse` 기능을 통해 `<p>` 를 `<form>` 태그로 전환되면 
수정할 댓글내용을 입력하고 수정 버튼을 클릭
3. Footer 의 `<script src="/js/app.js">`</script> 를 통해 [app.js](JavaScript%20%E1%84%8B%E1%85%AA%20%E1%84%8B%E1%85%B2%E1%84%92%E1%85%AD%E1%84%89%E1%85%A5%E1%86%BC%20%E1%84%80%E1%85%A5%E1%86%B7%E1%84%89%E1%85%A1%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB(app%20js)%20a7aa307c3044477393d4dd2cf4e80303.md) 접근하고
[app.js](JavaScript%20%E1%84%8B%E1%85%AA%20%E1%84%8B%E1%85%B2%E1%84%92%E1%85%AD%E1%84%89%E1%85%A5%E1%86%BC%20%E1%84%80%E1%85%A5%E1%86%B7%E1%84%89%E1%85%A1%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB(app%20js)%20a7aa307c3044477393d4dd2cf4e80303.md) 의 클릭 이벤트 메서드를 통해 유효성/공백 검사 후 data 설정된 이동 주소로 전송
    
    ![Untitled](%5BComment%5D%E1%84%83%E1%85%A2%E1%86%BA%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20+%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20%E1%84%8C%E1%85%A6%E1%84%92%E1%85%A1%E1%86%AB%2021f0668fb2c44907beadcfa9d6f4bbc4/Untitled%205.png)
    
4.  CommentApiController 에서 해당 주소로 매핑된 메서드가 실행 되어 
CommentService 의 메서드 호출
5. CommentService 에서는 CommentRepository 를 통해 받아온 정보를 
Comment 엔티티 클래스의 [메서드](%E1%84%83%E1%85%A9%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB(Entity)%204baede46aea54d9e8dd03bfd1914be3e.md)를 이용해 댓글 수정

**삭제**

1. 댓글 조회 로직을 통해 띄어준 댓글 목록 리스트의 **‘X’ 버튼(댓글 수정 버튼)** 클릭
2. Footer 의 `<script src="/js/app.js"></script>` 를 통해 [app.js](JavaScript%20%E1%84%8B%E1%85%AA%20%E1%84%8B%E1%85%B2%E1%84%92%E1%85%AD%E1%84%89%E1%85%A5%E1%86%BC%20%E1%84%80%E1%85%A5%E1%86%B7%E1%84%89%E1%85%A1%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB(app%20js)%20a7aa307c3044477393d4dd2cf4e80303.md) 접근하고
[app.js](JavaScript%20%E1%84%8B%E1%85%AA%20%E1%84%8B%E1%85%B2%E1%84%92%E1%85%AD%E1%84%89%E1%85%A5%E1%86%BC%20%E1%84%80%E1%85%A5%E1%86%B7%E1%84%89%E1%85%A1%20%E1%84%80%E1%85%AA%E1%86%AB%E1%84%85%E1%85%A7%E1%86%AB(app%20js)%20a7aa307c3044477393d4dd2cf4e80303.md) 의 클릭 이벤트 메서드를 작성자 본인 여부를 확인한 후 설정된 이동 주소로 이동
    
    ![Untitled](%5BComment%5D%E1%84%83%E1%85%A2%E1%86%BA%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20+%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20%E1%84%8C%E1%85%A6%E1%84%92%E1%85%A1%E1%86%AB%2021f0668fb2c44907beadcfa9d6f4bbc4/Untitled%206.png)
    
3.  CommentApiController 에서 해당 주소로 매핑된 메서드가 실행 되어 
CommentService 의 메서드 호출
4. CommentService 에서는 commentRepository 에 직접 생성한 메서드를 통해 comment 객체 정보를 받아오고 다시 commentRepository 의 JpaRepository 상속 메서드를 통해 삭제.

---

### comment/list.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      th:fragment="list">
<!-- Comments -->
<div class="card">
    <div class="card-header bi bi-chat-dots" >
        <span th:if="${comments}" th:text="${comments.size}">
        </span> Comments

    </div>
    <!-- 댓글내용 부분 -->
    <ul class="list-group-flush">
        <li th:each="comment : ${comments}" th:id="'comments-' + ${comment.id}" class="list-group-item">
            <span>
                <span style="font-size: small" th:text="${comment.nickname}"></span>
                <span style="font-size: xx-small" th:text="${comment.createdDate}"></span>
            </span>
            <span th:if="${isWriter}" >
                <a type="button" data-toggle="collapse" th:data-target="'.multi-collapse-' + ${comment.id}"
                   class="bi bi-pencil-square"></a> <!-- 댓글 수정 버튼 -->
                <a type="button" th:onclick="'main.commentDelete(' + ${posts.id} + ',' + ${comment.id} + ',' + ${comment.userId} + ',' + ${sessionId} + ')'"
                   class="bi bi-x-square"></a> <!-- 댓글 삭제 버튼 -->
            </span>

            <!-- 댓글 내용 보기 -->
            <p th:class="'collapse multi-collapse-'+${comment.id}+' show'" th:text="${comment.comment}"></p>

            <!-- 댓글 내용 수정 -->
            <form th:class="'collapse multi-collapse-'+${comment.id}">
                <input type="hidden" th:id="'id'" th:value="${comment.id}">
                <input type="hidden" th:id="'postsId'" th:value="${posts.id}">
                <input type="hidden" th:id="'writerUserId'" th:value="${comment.userId}">
<!--                <input type="hidden" th:id="'sessionUserId'" th:value="${sessionUserId}">-->
                <input type="hidden" th:if="${user}" th:id="'sessionUserId'" th:value="${user.id}">
                <div class="form-group">
                    <textarea class="form-control" th:id="'comment-content'" rows="3" th:text="${comment.comment}"></textarea>
                </div>
                <button type="button" id="btn-comment-update" class="btn btn-outline-primary bi bi-pencil-square"> 수정</button>
            </form>
        </li>
    </ul>
</div>
<br/>
</html>

```

<aside>
💡 Bootstrap의 `collapse` 기능

![Untitled](%5BComment%5D%E1%84%83%E1%85%A2%E1%86%BA%E1%84%80%E1%85%B3%E1%86%AF%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%86%E1%85%B5%E1%86%BE%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20+%20%E1%84%89%E1%85%AE%E1%84%8C%E1%85%A5%E1%86%BC%20%E1%84%89%E1%85%A1%E1%86%A8%E1%84%8C%E1%85%A6%20%E1%84%8C%E1%85%A6%E1%84%92%E1%85%A1%E1%86%AB%2021f0668fb2c44907beadcfa9d6f4bbc4/Untitled%207.png)

Bootstrap 에서 사용자가 클릭할 때 컨텐츠를 숨기거나 표시할 수 있게 해주는 인터랙티브 기능으로 숨기거나 표시할 요소에 `class="collapse ~ ”` 를 추가해 `collapse` 적용.

</aside>

### comment/form.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      th:fragment="form">
<div class="card">
    <div class="card-header bi bi-chat-right-dots"> Write a Comment</div>
    <!-- 댓글작성 부분 -->
    <form>
        <input type="hidden" th:id="postsId" th:value="${posts.id}">
        <div th:if="${user}" class="card-body">
            <textarea id="comment" class="form-control" rows="4" placeholder="댓글을 입력하세요"></textarea>
        </div>
        <div th:if="${user}" class="card-footer">
            <button type="button" id="btn-comment-save" class="btn btn-outline-primary bi bi-pencil-square"> 등록</button>
        </div>
        <div th:unless="${user}" class="card-body" style="font-size: small">
            <a th:href="@{/auth/login}">로그인</a>을 하시면 댓글을 등록할 수 있습니다.
        </div>
    </form>
</div>
</html>

```

---

### CommentApiController.java

댓글 관련 실제 API 관련 컨트롤러

```java
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Slf4j
public class CommentApiController {
    private final CommentService commentService;

   ...

    /* UPDATE */
    @PutMapping({"/posts/{postsId}/comments/{id}"})
    public ResponseEntity<Long> update(@PathVariable("postsId")Long postsId, @PathVariable("id")Long id, @RequestBody CommentDto.Request dto) {
        log.info("==========update전==========");
        log.info("postsId="+postsId +", id="+id+", dto="+dto);
        commentService.update(postsId, id, dto);
        log.info("==========update후==========");

        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Long> delete(@PathVariable("postId") Long postsId, @PathVariable("id") Long id) {
        commentService.delete(postsId, id);
        return ResponseEntity.ok(id);
    }
}
```

- `update()`
: 게시글 과 댓글의 `id`를 경로 변수로 받아와 `CommentDto` 와 함께 
서비스 클래스의 메서드 호출
    
    <aside>
    💡 **`@RequestBody`** 란?
    HTTP Request 본문을 읽어와서, 지정된 자바 객체로 변환해주는 역할. `HttpMessageConverter`를 통해 작업이 이뤄지며 SpringBoot 에서는 기본적으로 Jackson 라이브러리를 사용하여  JSON 데이터를 자바 객체로 변환해 준다.
    
    </aside>
    
- `delete()`
: 게시글 과 댓글의 `id`를 경로 변수로 받아와 서비스 클래스의 메서드 호출

---

### CommentService.java

이름으로 User객체를 찾아 게시물(Posts) Dto 의 엔티티로 바꿔준뒤 Repository 에서 엔티티 저장

```java
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostsRepository postsRepository;

   ...

    /* UPDATE */
    @Transactional
    public void update(Long postId, Long id, CommentDto.Request dto) {
        Comment comment = commentRepository.findByPostsIdAndId(postId, id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다." + id));
        comment.update(dto.getComment());
    }

    /* DELETE */
    public void delete(Long postId, Long id) {
        Comment comment = commentRepository.findByPostsIdAndId(postId, id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        commentRepository.delete(comment);
    }
}

```

- `update()` / `delete()`
: 두 메서드 모두 CommentRepository 의 직접 생성한 `findByPostsIdAndId` 메서드를 이용하는데 공통점이 있음. 하지만 `update()` 는 `Comment` 엔티티 클래스의 메서드로,  `delete()` 는 commentRepository 의 JpaRepository 로부터 상속 받은 기본 메서드로 접근하는데 있어 차이가 있다.

### CommentRepository.java

`JpaRepository<Comment, Long>` 를 상속받아 기본적인 CRUD 관련 메서드는 제공을 한다.

```java
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /* 게시글 댓글 목록 가져오기 */

    Optional<Comment> findByPostsIdAndId(Long postId, Long id);
}
```

- `Optional<Comment> findByPostsIdAndId(Long postId, Long id);`
: Spring Data JPA 를 통해 생성한 메서드의 이름을 분석하여 JPQL 쿼리를 자동으로 생성

---