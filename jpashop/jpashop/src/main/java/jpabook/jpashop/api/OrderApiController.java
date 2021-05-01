package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepo;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
  @GetMapping("/api/v1/orders")
  public Result orderV1() {
    List<Order> result = orderRepo.findAllWithFetch();
    List<OrderDto> collect = result.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());

    return new Result(collect);

  }

  //
  @GetMapping("api/v2/orders")
  public Result orderV2() {
    List<OrderQueryDto> orderDtos = orderQueryRepo.findOrderDtos();

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

    public OrderDto(Order order) {
      orderId = order.getId();
      name = order.getMember().getName();
      orderDate = order.getOrderDate();
      orderStatus = order.getOrderStatus();
      address = order.getDelivery().getAddress();
    }
  }
}
