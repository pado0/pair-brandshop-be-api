package minishop.project.controller;

import lombok.RequiredArgsConstructor;
import minishop.project.entity.Member;
import minishop.project.exception.CUserNotFoundException;
import minishop.project.repository.MemberRepository;
import minishop.project.service.ResponseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthTestController {

    private final MemberRepository memberRepository;
    private final ResponseService responseService;


    @GetMapping(value = "/member")
    public Member findUser() {
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return memberRepository.findByLoginEmail(id).orElseThrow(CUserNotFoundException::new);
    }

}