package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

//    public void save(Item item){
//        if(item.getId() == null){
//            em.persist(item);//새로운 객체 생성
//        }else {
//            em.merge(item);//Update 느낌. 준영속 엔티티를 병합
//        }
//    }
    public void save(Item item) {
        em.persist(item);
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
