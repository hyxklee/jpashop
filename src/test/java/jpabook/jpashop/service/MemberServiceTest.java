package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

   @Autowired
   MemberService memberService;

   @Autowired
   MemberRepository memberRepository;

   @Autowired
    EntityManager em;

    //회원가입 테스트
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");
        //when
        Long saveId = memberService.join(member);
        System.out.println("saveId = " + saveId);
        //then
        assertThat(member).isEqualTo(memberRepository.findOne(saveId));
//        assertEquals(member, memberRepository.findOne(saveId));//동일하게 실행
    }

    //중복 회원 예외
    @Test
    public void 중복회원확인() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
//        memberService.join(member2);//IllegalStateException 예외 발생
        //then
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다. ", thrown.getMessage());
    }
}