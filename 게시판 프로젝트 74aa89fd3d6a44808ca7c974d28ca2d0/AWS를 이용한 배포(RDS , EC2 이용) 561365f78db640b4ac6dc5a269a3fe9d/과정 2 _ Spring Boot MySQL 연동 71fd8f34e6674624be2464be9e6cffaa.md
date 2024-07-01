# 과정 2 _  Spring Boot / MySQL 연동

### **1. MySQL 을 연결 하기 위해 DB의 엔드포인트 정보를 복사.**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A5%E1%86%BC%202%20_%20Spring%20Boot%20MySQL%20%E1%84%8B%E1%85%A7%E1%86%AB%E1%84%83%E1%85%A9%E1%86%BC%2071fd8f34e6674624be2464be9e6cffaa/Untitled.png)

### **2. 연결을 설정하기 위해 DB Navigator 의 Setting 정보 설정.**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A5%E1%86%BC%202%20_%20Spring%20Boot%20MySQL%20%E1%84%8B%E1%85%A7%E1%86%AB%E1%84%83%E1%85%A9%E1%86%BC%2071fd8f34e6674624be2464be9e6cffaa/Untitled%201.png)

### **3. 정보를 입력하고 Test Connection 으로 확인 후 연결 실행**

![Untitled](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A5%E1%86%BC%202%20_%20Spring%20Boot%20MySQL%20%E1%84%8B%E1%85%A7%E1%86%AB%E1%84%83%E1%85%A9%E1%86%BC%2071fd8f34e6674624be2464be9e6cffaa/Untitled%202.png)

- Name 정보는 크게 상관 없음.
- Host 에는 복사한 엔드 포인트 정보 입력
- Database 는 ‘과정 1 _  RDS 설정’ 의 추가구성 > [초기 DB 이름 값](%E1%84%80%E1%85%AA%E1%84%8C%E1%85%A5%E1%86%BC%201%20_%20RDS%20%E1%84%89%E1%85%A5%E1%86%AF%E1%84%8C%E1%85%A5%E1%86%BC%20dc9657ea9e314023b7e63cf660bb689c.md) 입력
- User / Password 에는 마스터 이름 /  마스터 암호 정보를 입력.

### **4. 스프링 부트의 설정을 위해** **`application.properties` 파일 정보 설정**

```
spring.datasource.url=jdbc:mysql://엔드포인트:3306/db이름(myselectshop)
spring.datasource.username=마스터이름
spring.datasource.password=패스워드
spring.jpa.hibernate.ddl-auto=update
```