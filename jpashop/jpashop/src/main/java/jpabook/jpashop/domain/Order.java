package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {

  @Id @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  private LocalDateTime orderDate;

  //enum 타입 Order, Cancel 사용 예정
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  protected Order(){

  }

  // ==연관관계 메서드== //
  public void setMember(Member member) {
    this.member = member;
    member.getOrderList().add(this);
  }

  public void addOrderItem(OrderItem orderitem) {
    orderItems.add(orderitem);
    orderitem.setOrder(this);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }

  // ==생성 메서드== //

  public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);

    for(OrderItem orderItem: orderItems){
      order.addOrderItem(orderItem);
    }
    order.setOrderStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;

  }


  // == 비지니스 로직 == //
  public void cancel() {
    if(delivery.getDeliveryStatus() == DeliveryStatus.COMP){
      throw new IllegalStateException("이미 배송이 완료된 제품은 취소가 불가능합니다");
    }

    this.setOrderStatus(OrderStatus.CANCEL);

    // 재고 수량을 추가 해준다
    for(OrderItem orderItem: orderItems){
      orderItem.restoration();
    }

  }

  // ==조회 로직 == //
  // 전체 주문 가격을 조회
  public int getPrice() {
    int totalPrice = 0;
    for(OrderItem orderItem: orderItems){
      totalPrice = orderItem.getTotalPrice();
    }
    return totalPrice;
  }



}
