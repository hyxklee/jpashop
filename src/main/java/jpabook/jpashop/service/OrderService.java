package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Dictionary;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);//static 메서드

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);//static 메서드

        //주문 저장
        orderRepository.save(order);//orderitem과 delivery는 cascade 옵션에 의해 order가 저장되면 같이 저장됨

        return order.getId();
    }

    //주문 취소
    @Transactional
    public void cancelOrder(long orderId){
        //엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문취소
        order.cancel(); //비즈니스 로직을 order에 작성해줬기 떄문에 서비스에서 불러와서 사용하는 느낌
    }


    /*
    //검색
    public List<Order> findOrders(Ordersearch ordersearch){
        return orderRepository.findAll(ordersearch);
    }
    */
}
