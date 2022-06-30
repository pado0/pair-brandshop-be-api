package minishop.project.controller;

import lombok.RequiredArgsConstructor;
import minishop.project.dto.MemberGetDto;
import minishop.project.entity.Member;
import minishop.project.exception.CEmailSigninFailedException;
import minishop.project.mapper.MemberMapper;
import minishop.project.repository.MemberRepository;
import minishop.project.response.CommonResult;
import minishop.project.response.ListResult;
import minishop.project.response.SingleResult;
import minishop.project.config.security.JwtTokenProvider;
import minishop.project.service.ResponseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class MemberSignController {

    private final MemberRepository memberRepository; // jpa 쿼리 활용
    private final JwtTokenProvider jwtTokenProvider; // jwt 토큰 생성
    private final ResponseService responseService; // API 요청 결과에 대한 code, messageㅍ
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    private final MemberMapper memberMapper;

    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@RequestParam String id,
                                       @RequestParam String password) {
        Member member = memberRepository.findByLoginEmail(id).orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            // matches : 평문, 암호문 패스워드 비교 후 boolean 결과 return
            throw new CEmailSigninFailedException();
        }
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(member.getId()), member.getRoles()));
    }

    @PostMapping(value = "/signup/{role}")
    public CommonResult signup(@RequestParam String id,
                               @RequestParam String password,
                               @PathVariable String role) {

        // 권한 정보 잘못 들어왔을 경우 얼리리턴
        if(!role.equals("ROLE_USER") && !role.equals("ROLE_ADMIN"))
            return responseService.getFailResult(9999, "권한 정보가 잘못되었습니다.");

        memberRepository.save(Member.builder()
                .loginEmail(id)
                .loginPassword(passwordEncoder.encode(password))
                .roles(Collections.singletonList(role))
                .build());

        return responseService.getSuccessResult();
    }

    @GetMapping(value = "/members")
    public List<MemberGetDto> findUsers() {

        List<Member> members = memberRepository.findAll();
        // Dto mapping
        return members.stream().map(m -> memberMapper.memberToMemberGetDto(m)).collect(Collectors.toList());

        // stream foreach가 아닌 map으로 처리
        //members.stream().forEach(m -> memberGetDtos.add(memberMapper.memberToMemberGetDto(m)));
        //return memberGetDtos;

    }

}