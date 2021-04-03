package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.criteria.internal.OrderImpl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

  @Id @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;

  private int price;
  private int stackQuantity;

  @OneToMany(mappedBy = "item")
  private List<ItemCategory> itemList  = new ArrayList<>();

  // ==연관 관계 메서드== //

  public void addItemCategory(ItemCategory itemCategory) {
    itemList.add(itemCategory);
    itemCategory.setItem(this);
  }




}
