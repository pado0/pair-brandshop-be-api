package minishop.project.controller;

import lombok.RequiredArgsConstructor;
import minishop.project.entity.Member;
import minishop.project.exception.CEmailSigninFailedException;
import minishop.project.repository.MemberRepository;
import minishop.project.response.CommonResult;
import minishop.project.response.SingleResult;
import minishop.project.security.JwtTokenProvider;
import minishop.project.service.ResponseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final MemberRepository memberRepository; // jpa 쿼리 활용
    private final JwtTokenProvider jwtTokenProvider; // jwt 토큰 생성
    private final ResponseService responseService; // API 요청 결과에 대한 code, messageㅍ
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

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

    // todo: signup 파라미터에 따라 role user / role admin으로 변경, enum으로 관리하기
    @PostMapping(value = "/signup")
    public CommonResult signup(@RequestParam String id,
                               @RequestParam String password) {

        memberRepository.save(Member.builder()
                .loginEmail(id)
                .loginPassword(passwordEncoder.encode(password))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return responseService.getSuccessResult();
    }
}