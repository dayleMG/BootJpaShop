# 실전! 스프링 부트와 JPA 활용 - 웹 애플리케이션 개발
## 인프런 강의(김영한)

인프런 강의를 보고 따라한 예제입니다.
https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-JPA-%ED%99%9C%EC%9A%A9-1/dashboard
## Features

- 회원 기능
  - 회원 등록
  - 회원 조회
  - 상품 기능
- 상품 등록
  - 상품 수정
  - 상품 조회
  - 주문 기능
- 상품 주문
  - 주문 내역 조회
  - 주문 취소

## 도메인 모델과 테이블 설계

![테이블](https://user-images.githubusercontent.com/63579963/114380978-996f4180-9bc5-11eb-8df8-a314cf7880f8.png)

## Database configuration

원하는 DB종류 선택(현재 MySQL 사용)
```sh
jpashop/src/main/resources/application.yml

```

### 도메인 설명

- 회원(Member): 이름, 임베디드 타입인 주소(Address), 주문(Orders)리스트를 가진다.<br>

- 주문(Order): 상품(Item)을 주문 회원, 날짜, 배송 정보, 주문 상태를 가지고있다. 주문 상태는 열거형을 사용.
- 배송(Delivery): 주문시 하나의 배송 정보를 생성한다. 주문과 배송은 일대일 관계
- 주문 상품(OrderItem): 주문한 상품 정보, 주문 수량, 주문 금액 정보를 가지고 있다.
- 상품(Item): 이름, 가격, 재고 수량을 가지고 있다.상품을 주문하면 재고수량이 줄어든다.
- 상품의 종류는 dtype으로 도서 음반, 영화를 통합해서 하나의 테이블로 만들었다. 
