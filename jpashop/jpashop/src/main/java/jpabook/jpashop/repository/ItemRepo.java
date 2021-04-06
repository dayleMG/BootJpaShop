package jpabook.jpashop.repository;
import jpabook.jpashop.domain.Item;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepo {

  @PersistenceContext
  private EntityManager em;

  public void save(Item item){
    if(item.getId() == null){
      em.persist(item);
    } else {
      em.merge(item);
    }
  }

  public Item findOne(Long id){
    return em.find(Item.class, id);
  }

  public List<Item> findAll(){
    return em.createQuery("select i from Item i", Item.class)
            .getResultList();
  }
}
