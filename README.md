# Mission 1 내 위치 기반 공공 와이파이 정보를 제공하는 웹서비스 개발

## 프로젝트 소개
공공 데이터 자원을 끌어와서 내가 서비스 하고자 하는 데이터로 마이그레이션하여 웹을 기반으로 하여 원하는 형태로 출력하고자 함. 또한, 사용한 히스토리에 대해서 데이터베이스에 저장하는 기능과 이를 출력하는 기능을 구현하고자 함

## 개발 환경
- Version : Java 1.8
- IDE : Eclipse (Maven Project 이용, Tomcat v8.5, Dynamic web module version : 3.1)
- IDE library : okhttp3, gson, lombok
- Database : Mariadb (Tool : Dbeaver)
- ERD : exERD

## 주요 기능
1. 서울 열린데이터 광장에서 서울시 공공와이파이 서비스 위치 정보 API를 가져오기 (JSON 파일과 JSON 파일을 읽어 데이터로 변환하는 방식을 배움)
2. Mariadb를 통해 Database 생성 및 접근 권한 생성
3. ERD를 통해 논리/물리 모델을 작성하고 포워드 엔지니어링 기능을 통해 Dbeaver로 테이블 생성
4. Java를 이용해 Getter/Setter만을 포함한 Dto와 기능을 구현할 Model Class 생성
5. JSP를 생성하여 데이터베이스를 활용하는 웹페이지 구축

## 구현 시 실패한 점
- History페이지의 비고란 삭제버튼을 이용해 데이터를 삭제하는 기능을 구현해야했으나, 삭제버튼을 누르는 onclick으로 id값을 파라미터로 변환하지 못해 기능을 구현하지 못함. (Js에 대한 공부가 필요)
- 배포받은 템플릿에는 Dto, Model, Service로 나누어 Class를 관리했는데, Dto와 Model의 차이를 정확하게 이해하지 못함. (Dto와 Entity의 차이를 공부했으나 이해하지 못함.)

