package jpabook.jpashop.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id; //자동생성

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String  isbn;
}
