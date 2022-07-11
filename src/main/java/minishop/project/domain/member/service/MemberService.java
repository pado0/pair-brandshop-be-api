package minishop.project.domain.member.service;

import lombok.RequiredArgsConstructor;
import minishop.project.domain.member.dto.MemberInfoDto;
import minishop.project.domain.member.entity.Address;
import minishop.project.domain.member.entity.Member;
import minishop.project.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

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

    public Optional<Member> getMember(String email){
        return memberRepository.findByLoginEmail(email);
    }

    public void saveMember(String email, String password, String role){
        memberRepository.save(Member.builder()
                .loginEmail(email)
                .loginPassword(passwordEncoder.encode(password))
                .roles(Collections.singletonList(role))
                .build());
    }

    public List<Member> findAllMember(){
        return memberRepository.findAll();
    }
}
