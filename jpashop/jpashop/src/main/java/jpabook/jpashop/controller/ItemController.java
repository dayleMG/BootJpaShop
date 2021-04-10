package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Item;
import jpabook.jpashop.item.Book;

import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items/new")
  public String createForm(Model model){
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  public String create (BookForm bookForm){
    Book book = new Book();
    book.setName(bookForm.getName());
    book.setAuthor(bookForm.getAuthor());
    book.setIsbn(bookForm.getIsbn());
    book.setStockQuantity(bookForm.getStockQuantity());
    book.setPrice(bookForm.getPrice());

    itemService.saveItem(book);

    return "redirect:/";
  }

  @GetMapping("/items")
  public String list(Model model){
    List<Item> items = itemService.findAll();
    model.addAttribute("items", items);
    return "items/itemList";
  }

  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
    Book bookItem =(Book) itemService.findOne(itemId);

    BookForm bookForm = new BookForm();
    bookForm.setAuthor(bookItem.getAuthor());
    bookForm.setId(bookItem.getId());
    bookForm.setIsbn(bookItem.getIsbn());
    bookForm.setName(bookItem.getName());
    bookForm.setPrice(bookItem.getPrice());
    bookForm.setStockQuantity(bookItem.getStockQuantity());

    model.addAttribute("form", bookForm);
    return "items/updateItemForm";

  }

  @PostMapping("/items/{itemId}/edit")
  public String updateItem(@ModelAttribute("form") BookForm bookForm, @PathVariable Long itemId) {

    itemService.updateItem(itemId, bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());;

    return "redirect:/items";

  }

}

