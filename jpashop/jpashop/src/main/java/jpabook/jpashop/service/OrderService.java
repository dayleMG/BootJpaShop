package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.ItemRepo;
import jpabook.jpashop.repository.OrderRepo;
import jpabook.jpashop.repository.MemberRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepo orderRepo;
  private final ItemRepo itemRepo;
  private final MemberRepo memberRepo;

  //주문

  @Transactional
  public Long order(Long memberid, Long itemId, int count) {
    //엔티티 조회
    Member member = memberRepo.findOne(memberid);
    Item item = itemRepo.findOne(itemId);

    //배송 정보
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    //주문 상품 생성
    OrderItem orderItem = OrderItem.createOrder(item, item.getPrice(), count);

    //주문 생성
    Order order = Order.createOrder(member, delivery, orderItem);

    //주문 저장
    orderRepo.save(order);

    return order.getId();

  }


  @Transactional
  //취소
  public void cancelOrder(Long orderId) {
    // 주문 엔티티 조회
    Order order = orderRepo.findOne(orderId);

    // 주문 취소
    order.cancel();

  }


}
