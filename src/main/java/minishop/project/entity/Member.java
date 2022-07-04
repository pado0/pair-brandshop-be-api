package minishop.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import minishop.project.e.domain_eom.JpaBaseEntity;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// todo : 엔티티 자체에 만료확인같은 처리가 들어간다. 비즈니스로직!?

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Post Entity에서 User와의 관계를 Json으로 변환시 오류 방지를 위한 코드
@Proxy(lazy = false)
public class Member extends JpaBaseEntity implements UserDetails {

    @Id @GeneratedValue
    private Long id; // todo: 이부분 String + 임의값으로 리펙토링하기

    @Column(nullable = false, unique = true, length = 50)
    private String loginEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 100)
    private String loginPassword;

    @Embedded
    private Address address;

    private String phoneNumber;



    // 멤버가 가진 권한 정보 리스트로 저장
    @ElementCollection(fetch = FetchType.EAGER )// todo: 왜 이렇게 선언하는지 확인
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    // todo : getpassword는 막아야되나?
    @Override
    public String getPassword() {
        return this.loginPassword;
    }

    // todo : JsonProperty가 뭔지 확인하기
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.loginEmail;
    }

    // 계정이 만료되었는지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계쩡이 잠기지 않았는지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 패스워드가 만료되진 않았는지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 사용 가능한 상태인지
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
