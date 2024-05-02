package jpabook.jpashop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.form.BookForm;
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
@Tag(name = "Response Estimate", description = "Response Estimate API")
public class ItemController {

    private final ItemService itemService;

    /*
    item 등록 뷰
     */
    @GetMapping("/items/new")
    public String createForm(Model model){

        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = new Book();

        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    /*
    Item 조회 뷰
     */
    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList"; //HTML에서 List를 받아 반복하며 출력
    }


    /*
    Item 수정 뷰
     */

    @GetMapping("items/{itemId}/edit")
    public String UpdateItemForm(@PathVariable("itemId") Long itemId, Model model){
        //@PathVariable: 뷰에서 id를 바인딩하여 매개변수로 가져오는 어노테이션
        Book item = (Book) itemService.findOne(itemId); //바인딩한 id를 이용해 item을 조회
        BookForm form = new BookForm();

        //조회한 item의 정보를 form에 담아서 뷰에 출력
        form.setId(item.getId());
        form.setName(item.getName());
        form.setStockQuantity(item.getStockQuantity());
        form.setPrice(item.getPrice());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }
//    /*
//    뷰에서 바인딩한 item을 가져와서 뷰로 보여주고 같은 item에 대한 재저장
//     */
//
//    @PostMapping("/items/{itemid}/edit")
//    public String updateItem(@ModelAttribute("form") BookForm form){
//        Book book = new Book();
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//
//        itemService.saveItem(book);//id가 같으니까 업데이트 되는 효과일 듯. ItemRepository에서 merge 설정을 해뒀음
//        return "redirect:/items";
//    }

    /*
    컨트롤러에서 엔티티를 생성해서 저장하지 않고 트랜잭션이 있는 서비스 계층에 전달하여 서비스 계층에서 변경. 저장
     */

    /*
    수정한 코드
     */
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form){
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }
}
