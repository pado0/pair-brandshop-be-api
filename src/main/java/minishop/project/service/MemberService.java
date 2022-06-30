package minishop.project.service;

import lombok.RequiredArgsConstructor;
import minishop.project.dto.MemberInfoDto;
import minishop.project.entity.Member;
import minishop.project.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findMemberById(Long id){
        return memberRepository.findById(id);
    }

    // 수정은 여기서. service에서 dto를 의존해도 되는지?
    public Member updateMember(Long id, MemberInfoDto memberInfoDto) {
        Optional<Member> member = memberRepository.findById(id);
        // mapper로 셋?
        return null;
    }
}
