package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
  @Id @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String name;

  // 멀티쓰레드 환경에서 동시에 같은 아이디 생성 방지용
  @Column(unique = true)
  private String userId;

  @Embedded
  private Address address;

  @OneToMany(mappedBy = "member")
  private List<Order> orderList = new ArrayList<>();


  // ==비지니스 로직== //

  public void createMember(String name, String userId) {
    this.name = name;
    this.userId = userId;
  }

  public void updateName(String name) {
    this.name = name;
  }

}
