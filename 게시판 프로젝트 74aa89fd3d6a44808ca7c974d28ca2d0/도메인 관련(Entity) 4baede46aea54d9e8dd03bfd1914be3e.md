# 도메인 관련(Entity)

### Role.java

```java
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    SOCIAL("ROLE_SOCIAL");
    private final String value;

}
```

<aside>
💡 Enum 이란?
상수들의 집합을 정의하는데 사용되는 Data Type 으로 관련된 상수들을 묶어서 관리하는데 주로 사용된다. Enum에 메서드를 정의하기도 하거나 필드나 생성자를 정의 하여 활용 할 수 있다.

</aside>

---

### Comment.java

User 와 N대1 양방향 관계 , Posts 와 N대1 단방향 관계

```java
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment; //댓글 내용

    @Column(name = "create_date")
    @CreatedDate
    private String createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private String modifiedDate;

    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /* 댓글 수정 */
    public void  update(String comment) {
        this.comment = comment;
    }

}
```

- `@Builer`
:  Builder Pattern 을 쉽게 구현 할수 있도록 도와주는 Annotation 으로 다음의 특징이 있음
    1. 객체를 생성할 때 **Method Chaining 방식**으로 필드를 설정 할 수 있게 해 가독성을 놉힘
        
        ```java
        User user = User.builder()
                        .name("John Doe")
                        .age(30)
                        .email("john.doe@example.com")
                        .build();
        ```
        
    2. 필요하지 않은 필드는 설정하지 않아도 가능하게 하여 유연한 객체를 생성

---

### Posts.java

User 와 N대1 단방향 관계 

```java
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500 , nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT" , nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "integer default 0" , nullable = false)
    private int view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") //댓글 정렬
    private List<Comment> comments;

    /* 게시글 수정 */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
```

- `@NoArgsConstructor`
:  파라미터가 **없는** 기본 생성자를 자동으로 생성
- `@AllArgsConstructor`
: Lombok 라이브러리에서 제공하는 Annotation으로 
클래스의 **모든 필드**를 인수로 갖는 생성자를 자동으로 생성

---

### User.java

```java
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 30 , unique = true)
    private String username; //아이디

    @Column(nullable = false , unique = true)
    private String nickname;

    @Column(length = 100)
    private String password;

    @Column(nullable = false , length = 50 ,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /* 회원정보 수정 */
    public void modify(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
    /* 소셜로그인시 이미 등록된 회원이라면 수정날짜만 업데이트해줘서
     * 기존 데이터를 보존하도록 예외처리 */
    public User updateModifiedDate() {
        this.onPreUpdate(); /* 상속 받은 상위 클래스(BaseTimeEntity) 메서드*/
        return this;
    }

    public String getRoleValue() {
        return this.role.getValue();
    }

}
```

- `@JoinColumn`
: JPA에서 사용 되는 Annotation으로 두 Entity 간의 관계를 정의 할 때 사용 되며 
Foreign Key 로서 **참조**되는 Column 을 지정할 때 사용.
    - 주요 속성
        - Name : 현재 엔티티의 외래 키 열 이름을 지정
        - ReferencedColumnName : 참조되는 엔티티의 기본 키 열 이름을 지정
        - Nullable : 외래 키가 `NULL` 값을 가질 수 있는지 여부를 지정
        - Insertable / Updatable : 외래 키 열이 삽입 / 수정 될수 있는지 여주를 지정
        - Unique : 외래 키 열이 유니크한지 여부를 지정
        - ColumnDefinition : 데이터베이스의 열 정의를 직접 지정
- `@Enumerated`
: JAVA enum 타입을 매핑할 때 사용.
    - 주요 속성
        - `EnumType.ORDINAL` : enum 순서를 기준으로 데이터베이스에 저장
        - `EnumType.STRING` : enum 이름을 기준으로 데이터베이스에 저장

---