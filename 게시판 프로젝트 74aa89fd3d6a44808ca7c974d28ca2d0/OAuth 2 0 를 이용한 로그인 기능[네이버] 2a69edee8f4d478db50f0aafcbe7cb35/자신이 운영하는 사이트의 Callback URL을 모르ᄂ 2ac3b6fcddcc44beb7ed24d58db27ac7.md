# 자신이 운영하는 사이트의 Callback URL을 모르는 경우

1. 브라우저를 통해 서비스 사이트 접속
2. 네이버 로그인 접근시 Callback URL 오류가 발생하는 화면으로 이동
    
    ![Untitled](Untitled%205.png)
    
3. 오류 메시지 화면에서 F12를 눌러 개발자 Concole 화면으로 이동하여 
Console 입력 프롬프트에 아래 명령어를 입력
    
    ![Untitled](%E1%84%8C%E1%85%A1%E1%84%89%E1%85%B5%E1%86%AB%E1%84%8B%E1%85%B5%20%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%BC%E1%84%92%E1%85%A1%E1%84%82%E1%85%B3%E1%86%AB%20%E1%84%89%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%90%E1%85%B3%E1%84%8B%E1%85%B4%20Callback%20URL%E1%84%8B%E1%85%B3%E1%86%AF%20%E1%84%86%E1%85%A9%E1%84%85%E1%85%B3%E1%84%82%202ac3b6fcddcc44beb7ed24d58db27ac7/Untitled.png)
    
4. 반환된 **Redirect_uri**  를 네이버 개발자 센터 애플리케이션에 Callback URL로 입력

![Untitled](%E1%84%8C%E1%85%A1%E1%84%89%E1%85%B5%E1%86%AB%E1%84%8B%E1%85%B5%20%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%A7%E1%86%BC%E1%84%92%E1%85%A1%E1%84%82%E1%85%B3%E1%86%AB%20%E1%84%89%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%90%E1%85%B3%E1%84%8B%E1%85%B4%20Callback%20URL%E1%84%8B%E1%85%B3%E1%86%AF%20%E1%84%86%E1%85%A9%E1%84%85%E1%85%B3%E1%84%82%202ac3b6fcddcc44beb7ed24d58db27ac7/Untitled%201.png)