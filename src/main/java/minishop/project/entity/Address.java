package minishop.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;
    private String phoneNumber;

    protected Address(){ }
}
