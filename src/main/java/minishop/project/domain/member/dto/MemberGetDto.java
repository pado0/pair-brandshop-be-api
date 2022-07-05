package minishop.project.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import minishop.project.domain.member.entity.Address;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MemberGetDto {

    private Long id;
    private String loginEmail;
    private Address address;
    private String phoneNumber;
    private List<String> roles = new ArrayList<>();

}
