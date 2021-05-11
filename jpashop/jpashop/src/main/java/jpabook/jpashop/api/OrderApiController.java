package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepo;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne
 * Order -> Memeber
 * Order -> Delivery
 */

@RestController
@RequiredArgsConstructor
public class OrderApiController {

  private final OrderRepo orderRepo;
  private final OrderQueryRepo orderQueryRepo;

  // 엔티티를 DTO로 변환후 성능 최적화를 위해 페치 조인을 이용한 방법
  // 페이징 기능 미지원(컬렉션이(1:N 관계) 포함되어 있기 떄문에)
  @GetMapping("/api/v1/orders")
  public Result orderV1() {
    List<Order> result = orderRepo.findAllWithFetch();
    List<OrderDto> collect = result.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());

    return new Result(collect);

  }

  //페이징 기능 지원 hibernate.default_batch_fetch_size 적용 SQL IN으로 한번에 가져온다
  @GetMapping("api/v1.2/orders")
  public Result orderV1Page(
          @RequestParam(value = "offset", defaultValue = "0") int offset,
          @RequestParam(value = "limit", defaultValue = "100") int limit) {
    List<Order> result = orderRepo.findAllWithBatch(offset, limit);
    List<OrderDto> collect = result.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());

    return new Result(collect);

  }

  // JPA에서 DTO 직접 조회 방식
  @GetMapping("api/v2/orders")
  public Result orderV2() {
    List<OrderQueryDto> orderDtos = orderQueryRepo.findOrderQueryDtos();

    return new Result(orderDtos);

  }

  @Data
  @AllArgsConstructor
  static class Result<T> {
    private T data;
  }

  @Data
  static class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItem;

    public OrderDto(Order order) {
      orderId = order.getId();
      name = order.getMember().getName();
      orderDate = order.getOrderDate();
      orderStatus = order.getOrderStatus();
      address = order.getDelivery().getAddress();
      // 프록시 객체에 값을 넣어줘야함
      orderItem = order.getOrderItems().stream()
              .map(oi -> new OrderItemDto(oi))
              .collect(Collectors.toList());
    }
  }

  @Data
  static class OrderItemDto {
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem){
      itemName = orderItem.getItem().getName();
      orderPrice = orderItem.getOrderPrice();
      count = orderItem.getCount();
    }
  }
}
