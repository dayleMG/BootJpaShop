package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.item.Book;
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

  /*
   * 영속성 컨텍스트가 자동 변경
   * 변경 감지 기법
   */

  @Transactional
  public void updateItem(Long itemId, String name, int price, int stockQuantity) {
    Item findItem = itemRepo.findOne(itemId);
    findItem.changeItem(name, price, stockQuantity);

  }

  public List<Item> findAll(){
    return itemRepo.findAll();
  }

  public Item findOne(Long itemId){
    return itemRepo.findOne(itemId);
  }
}
