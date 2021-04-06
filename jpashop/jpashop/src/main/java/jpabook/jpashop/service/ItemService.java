package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepo itemRepo;
  @Transactional
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
