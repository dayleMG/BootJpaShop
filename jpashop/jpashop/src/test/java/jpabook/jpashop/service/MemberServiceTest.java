package jpabook.jpashop.service;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
  @Autowired MemberService memberService;
  @Autowired
  MemberRepo memberRepository;
  @Test
  public void 회원가입() throws Exception {
    //Given
    Member member = new Member();
    member.setUserId("kim");
    //When
    Long saveId = memberService.join(member);
    //Then
    assertEquals(member, memberRepository.findOne(saveId));
  }
  @Test(expected = IllegalStateException.class)
  public void 중복_회원_예외() throws Exception {
    //Given
    Member member1 = new Member();
    member1.setUserId("kim");
    Member member2 = new Member();
    member2.setUserId("kim");
    //When
    memberService.join(member1);
    memberService.join(member2); //예외가 발생해야 한다.
    //Then
    fail("예외가 발생해야 한다.");
  }
}