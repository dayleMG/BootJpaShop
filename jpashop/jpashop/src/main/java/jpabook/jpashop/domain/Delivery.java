package jpabook.jpashop.domain;

import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

  @Id @GeneratedValue
  @Column(name = "delivery_id")
  private Long id;

  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
  private Order order;

  @Embedded
  private Address address;

  // Ordinal 절대 쓰지 말것 늘어나면 장애남
  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus; //READY, COMP




}
