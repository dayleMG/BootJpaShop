package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.repository.ItemRepo;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class itemService {
  private final ItemRepo itemRepo;

  public void saveItem(Item item){
    itemRepo.save(item);
  }

  public List<Item> findAll(){
    return itemRepo.findAll();
  }

  public Item findOne(Long itemId){
    return itemRepo.findOne(itemId);
  }
}
