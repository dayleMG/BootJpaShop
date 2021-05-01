package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepo {

  private final EntityManager em;

  public List<OrderQueryDto> findOrderDtos() {
    return em.createQuery(
            "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.orderStatus, d.address)" +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d", OrderQueryDto.class)
            .getResultList();
  }



}
