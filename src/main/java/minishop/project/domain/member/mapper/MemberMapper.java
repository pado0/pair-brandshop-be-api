package minishop.project.domain.member.mapper;

import minishop.project.domain.member.dto.MemberGetDto;
import minishop.project.domain.member.dto.MemberInfoDto;
import minishop.project.domain.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // 스프링 빈으로 등록됨
public interface MemberMapper {

    Member memberGetDtoToMember(MemberGetDto memberGetDto);

    MemberGetDto memberToMemberGetDto(Member member);

    MemberInfoDto memberToMemberInfoDto(Member member);
}
