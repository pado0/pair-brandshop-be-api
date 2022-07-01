package minishop.project.controller;

import lombok.RequiredArgsConstructor;
import minishop.project.dto.MemberInfoDto;
import minishop.project.entity.Member;
import minishop.project.mapper.MemberMapper;
import minishop.project.response.SingleResult;
import minishop.project.service.MemberService;
import minishop.project.service.ResponseService;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class MemberInfoController {

    private final MemberService memberService;
    private final ResponseService responseService;

    private final MemberMapper memberMapper;
    // 멤버 정보 입력 api -> 회원가입과 분리
    // id / pw는 변경 불가, 나머지 정보만 받도록 dto에서는 email/pw 정보 받지 않음
    @PutMapping("/member/{id}")
    public MemberInfoDto updateMemberInfo(@RequestBody MemberInfoDto memberInfoDto,
                                                  @PathVariable Long id) {

        Optional<Member> updatedMemberInfo = memberService.updateMember(id, memberInfoDto);

        MemberInfoDto memberInfoResponse = new MemberInfoDto(
                updatedMemberInfo.get().getAddress().getCity(),
                updatedMemberInfo.get().getAddress().getStreet(),
                updatedMemberInfo.get().getAddress().getZipcode(),
                updatedMemberInfo.get().getPhoneNumber());
        return memberInfoResponse;
    }

    // todo : 탈퇴처리 - db에서 연관된 모든 데이터 날리기?

}
