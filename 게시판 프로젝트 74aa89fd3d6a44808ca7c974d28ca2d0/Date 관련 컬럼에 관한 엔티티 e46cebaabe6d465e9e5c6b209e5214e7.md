# Date 관련 컬럼에 관한 엔티티

### BaseTimeEntity

공통으로 사용되는 컬럼에 관하여 편의를 위해 따로 구현한 class

```java
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @Column(name = "created_date",nullable = false)
    @CreatedDate
    private String createdDate;

    @Column(name = "modified_date",nullable = false)
    @LastModifiedDate
    private String modifiedDate;

    /* 해당 엔티티를 저장하기 이전에 실행 */
    @PrePersist
    public void onPrepersist() {
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.modifiedDate = this.createdDate;
    }

    /* 해당 엔티티를 업데이트 하기 이전에 실행*/
    @PreUpdate
    public void onPreUpdate() {
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }
}
```

- `@EntityListeners`
`@EntityListeners` 는 JPA 에서 Entity 생명주기 이벤트를 리스닝하고 처리하는데 사용되는 Annotation이며 Spring Data JPA 에서는 엔티티의 특정 상태 변경 이벤트에 대응하는데 사용.
    
    주로 엔티티 클래스에 추가하여 (`@PrePersist`, `@PostPersist`, `@PreRemove` , …) 같은 Annotation으로  해당 엔티티의 생명주기 이벤트를 리스닝 하여 사용된다.
    
- `@CreatedDate` / `@LastModifiedDate`
`@CreatedDate` 는 엔티티가 **처음 생성될 때**의 날짜와 시간을 자동으로 설정하여 엔티티가 처음 저장되는 시점을 기록하는데 사용 되고
`@LastModifiedDate` 는 엔티티가 **마지막으로 수정될 때**의 날짜와 시간을 자동으로 설정하여 엔티티가 수정되는 시점을 기록하는데 사용 된다.
    
    > 예제에서는 `@EntityListeners` 로 감사(Auditing) Listener 를 활성화 한뒤 
    `@CreatedDate` 와 `@LastModifiedDate` 를 통해 엔티티의 생성 시점과 마지막 수정 시점을 자동으로 관리하게 사용 되었다.
    > 

<aside>
💡 Listener(리스너)?
 "리스너"는 특정 **이벤트가 발생**할 때 자동으로 호출되는 메서드를 제공하여 그 이벤트에 대해 반응하는 기능을 구현하는 구성 요소이며 ‘이벤트 리스너’ 라고도 한다.

</aside>