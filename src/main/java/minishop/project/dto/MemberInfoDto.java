package minishop.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberInfoDto {

    // address
    private String city;
    private String street;
    private String zipcode;

    // phone
    private String phoneNumber;

}
