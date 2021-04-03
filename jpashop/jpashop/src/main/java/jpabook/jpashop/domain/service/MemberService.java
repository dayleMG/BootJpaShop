package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepo memberRepo;

  @Transactional(readOnly = false)
  //회원 가입
  private Long join(Member member) {
    //중복 아이디 체크
    validateDuplicateUserId(member);
    memberRepo.save(member);
    return member.getId();

  }

  public void validateDuplicateUserId(Member member) {
    List<Member> byUserId = memberRepo.findByUserId(member.getUserId());
    if(!byUserId.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 아이디 입니다");
    }
  }

  //회원 전체 조회
  public List<Member> findMembers() {
    return memberRepo.findAll();
  }

  public Member findOne(Long memberId) {
    return memberRepo.findOne(memberId);
  }
}
