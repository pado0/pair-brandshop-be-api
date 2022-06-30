package minishop.project.mapper;

import minishop.project.dto.MemberGetDto;
import minishop.project.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // 스프링 빈으로 등록됨
public interface MemberMapper {

    Member memberGetDtoToMember(MemberGetDto memberGetDto);

    MemberGetDto memberToMemberGetDto(Member member);

}
