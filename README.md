git 커밋 테스트
`spring config`
- spring version = 3.2.3 
- java version = 17


`h2 Database 설치 및 연결(windows 기준)`
<h3>1. [h2database](https://h2database.com/html/main.html) 접속</h3>

--- 


<h3>2. PC 운영체제에 맞는 설치 파일 다운로드 </h3>


---

<h3>3. h3-setup.exe 파일 압축 해제 </h3>

---

<h3>4. cmd 혹은 파일 탐색기에서 C:\Program Files (x86)\h3\bin (압축 해제 시 설치 경로) 경로로 이동</h3>

---
<h3>5. h3.bat 파일 실행</h3>

---
<h3>6. 브라우저에서 h3 콘솔 실행 되면 port 앞 부분 지우고 localhost 변경 후 엔터</h3>
    - ex ) localhost:8082/login~~~~

---
<h3>7. 최초 접속 시 JDBC URL: jdbc:h3:~/test 입력 후 연결</h3>

---
<h3>8. home 경로에 test.mv.db 생성 후 JDBC URL : jdbc:h3:tcp://localhost/~/test 로 연결 후 사용</h3>
- 여러 군데서 접근할 수 있도록 설정 변경
---



