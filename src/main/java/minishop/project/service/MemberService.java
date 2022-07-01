package minishop.project.service;

import lombok.RequiredArgsConstructor;
import minishop.project.dto.MemberInfoDto;
import minishop.project.entity.Address;
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
    @Transactional
    public Optional<Member> updateMember(Long id, MemberInfoDto memberInfoDto) {
        Optional<Member> member = memberRepository.findById(id);

        Address requestAddress = new Address(memberInfoDto.getCity(),
                                            memberInfoDto.getStreet(),
                                            memberInfoDto.getZipcode());

        member.get().setAddress(requestAddress);
        member.get().setPhoneNumber(memberInfoDto.getPhoneNumber());

        return member;
    }
}
