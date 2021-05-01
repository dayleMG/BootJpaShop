package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

  // 동적 쿼리 추후에 Querydsl로 바꿀 것
  public List<Order> findAll(OrderSearch orderSearch) {
    //language=JPAQL
    String jpql = "select o From Order o join o.member m";
    boolean isFirstCondition = true;
    //주문 상태 검색
    if (orderSearch.getOrderStatus() != null) {
      if (isFirstCondition) {
        jpql += " where";
        isFirstCondition = false;
      } else {
        jpql += " and";
      }
      jpql += " o.orderStatus = :orderStatus";
    }
    //회원 이름 검색
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      if (isFirstCondition) {
        jpql += " where";
        isFirstCondition = false;
      } else {
        jpql += " and";
      }
      jpql += " m.name like :name";
    }
    TypedQuery<Order> query = em.createQuery(jpql, Order.class)
            .setMaxResults(1000); //최대 1000건
    if (orderSearch.getOrderStatus() != null) {
      query = query.setParameter("orderStatus", orderSearch.getOrderStatus());
    }
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      query = query.setParameter("name", orderSearch.getMemberName());
    }
    return query.getResultList();
  }

  public List<Order> findAllWithFetch() {
    return em.createQuery(
            "select o from Order o" +
                    " join fetch o.member m" +
                    " join fetch o.delivery d", Order.class)
            .getResultList();
  }

}
