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

    // todo: loadUserByUsername 부분 레퍼지토리랑 잘 연결되는지 확인하기, 커스텀 exception으로 변경하기
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            return memberRepository.findById(Long.valueOf(id)).orElseThrow(Exception::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
