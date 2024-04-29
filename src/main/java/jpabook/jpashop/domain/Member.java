package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")//멤버가 1. 오더가 n. 이므로 자기기준으로 작성. mappdeBy를 통해 주인을 지정.
    private List<Order> orders = new ArrayList<>();

}
