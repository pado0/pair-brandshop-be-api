package minishop.project.api;

import lombok.RequiredArgsConstructor;
import minishop.project.domain.member.dto.MemberGetDto;
import minishop.project.domain.member.entity.Member;
import minishop.project.domain.member.service.MemberService;
import minishop.project.exception.CEmailSigninFailedException;
import minishop.project.domain.member.mapper.MemberMapper;
import minishop.project.domain.member.repository.MemberRepository;
import minishop.project.api.response.CommonResult;
import minishop.project.api.response.SingleResult;
import minishop.project.config.security.JwtTokenProvider;
import minishop.project.domain.common.ResponseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MemberSignController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider; // jwt 토큰 생성
    private final ResponseService responseService; // API 요청 결과에 대한 code, message
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    private final MemberMapper memberMapper;

    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@RequestParam String email,
                                       @RequestParam String password) {
        Member member = memberService.getMember(email).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            // matches : 평문, 암호문 패스워드 비교 후 boolean 결과 return
            throw new CEmailSigninFailedException();
        }
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(member.getId()), member.getRoles()));
    }

    @PostMapping(value = "/signup/{role}")
    public CommonResult signup(@RequestParam String email,
                               @RequestParam String password,
                               @PathVariable String role) {

        // 권한 정보 잘못 들어왔을 경우 얼리리턴
        if(!role.equals("ROLE_USER") && !role.equals("ROLE_ADMIN"))
            return responseService.getFailResult(9999, "권한 정보가 잘못되었습니다.");

        memberService.saveMember(email, password, role);

        return responseService.getSuccessResult();
    }

    @GetMapping(value = "/members")
    public List<MemberGetDto> findUsers() {

        List<Member> members = memberService.findAllMember();
        // Dto mapping
        return members.stream().map(memberMapper::memberToMemberGetDto).collect(Collectors.toList());

    }

}