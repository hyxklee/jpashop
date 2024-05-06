package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    /*
    변경 감지를 이용한 아이템 정보 업데이트
     */
    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(id); // 영속 상태의 엔티티를 찾아옴 (em.find())
        item.change(name, price, stockQuantity); //set을 남발하기 보단 의미있는 메소드를 사용하여 로직을 구현하는 것이 좋음
//        item.setName(name);
//        item.setPrice(price);
//        item.setStockQuantity(stockQuantity);
        //영속 엔티티를 조회하여 수정했기 때문에 변경 감지가 일어나 JPA가 자동으로 UPDATE 쿼리를 생성
    }
}
