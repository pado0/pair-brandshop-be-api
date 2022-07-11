package minishop.project.domain.security;

import lombok.RequiredArgsConstructor;
import minishop.project.domain.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 토큰에 세팅된 유저 정보로 회원 정보를 조회하는 서비스
@RequiredArgsConstructor
@Service
public class CustomMemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            return memberRepository.findById(Long.valueOf(id)).orElseThrow(Exception::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
