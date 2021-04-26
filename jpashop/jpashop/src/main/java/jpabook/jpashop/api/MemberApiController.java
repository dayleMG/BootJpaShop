package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.metal.MetalMenuBarUI;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;

  @GetMapping("api/v1/members")
  public List<Member> memebersV1() {
    return memberService.findMembers();
  }

  @GetMapping("api/v2/members")
  public Result membersV2() {
    List<Member> findMember = memberService.findMembers();
    List<MemberDto> collect = findMember.stream()
            .map(m -> new MemberDto(m.getName()))
            .collect(Collectors.toList());

    return new Result(collect);

  }

  @Data
  @AllArgsConstructor
  static class Result<T> {
    private T data;
  }

  @Data
  @AllArgsConstructor
  static class MemberDto{
    private String name;
  }

  @PostMapping("/api/members")
  public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request){

    Member member = new Member();
    member.createMember(request.getName(), request.getUserId());

    Long id = memberService.join(member);
    return new CreateMemberResponse(id);

  }

  @PutMapping("/api/members/{id}")
  public UpdateMemberResponse updateMember(@PathVariable("id") Long id,
                                           @RequestBody @Valid UpdateMemberRequest request) {

    memberService.update(id, request.getName());
    Member findMember = memberService.findOne(id);
    return new UpdateMemberResponse(findMember.getId(), findMember.getName());

  }

  @Data
  @AllArgsConstructor
  static class UpdateMemberResponse {
    private Long id;
    private String name;
  }

  @Data
  static class UpdateMemberRequest {
    private String name;
  }

  @Data
  static class CreateMemberRequest {
    private String name;
    private String userId;

  }

  @Data
  static class CreateMemberResponse {
    private Long id;

    public CreateMemberResponse(Long id) {
      this.id = id;
    }
  }
}
