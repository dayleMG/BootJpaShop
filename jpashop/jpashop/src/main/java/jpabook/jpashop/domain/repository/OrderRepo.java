package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository

public class OrderRepo {

  @PersistenceContext
  private EntityManager em;

  public void save(Order order){
    em.persist(order);
  }

  public Order findOne(Long id) {
    return em.find(Order.class, id);
  }

}
