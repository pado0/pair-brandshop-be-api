package minishop.project.controller;

import lombok.RequiredArgsConstructor;
import minishop.project.dto.MemberInfoDto;
import minishop.project.entity.Member;
import minishop.project.response.SingleResult;
import minishop.project.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberInfoController {

    private final MemberService memberService;

    // 멤버 정보 입력 api -> 회원가입과 분리
    // id / pw는 변경 불가, 나머지 정보만 받도록 dto에서는 email/pw 정보 받지 않음
    @PutMapping("/member/{id}")
    public SingleResult<Member> updateMemberInfo(@RequestBody MemberInfoDto memberInfoDto,
                                                 @PathVariable Long id) {
        // dto 를 memberfh qkrnrh ? ?
        memberService.updateMember(id, memberInfoDto);
        return  null; // todo : 리턴 처리하기
    }

}

