package minishop.project.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import minishop.project.domain.common.JpaBaseEntity;
import minishop.project.domain.like.entity.Like;
import minishop.project.domain.order.entity.Order;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Post Entity에서 User와의 관계를 Json으로 변환시 오류 방지를 위한 코드
@Proxy(lazy = false)
public class Member extends JpaBaseEntity implements UserDetails {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String loginEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 100)
    private String loginPassword;

    @Embedded
    private Address address;

    private String phoneNumber;




    // 멤버가 가진 권한 정보 리스트로 저장
    // RDB는 컬렉션 형태를 저장할 수 없으므로 본 어노테이션을 사용해 별도 테이블로 권한을 관리해준다.
    @ElementCollection(fetch = FetchType.EAGER )
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.loginPassword;
    }

    // JsonProperty가 뭔지 확인하기 : 응답 요청, 쓰기일때만 확인하고, 응답값엔 포함하지 않는다.
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



    /*
     *  준승
     */

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    List<Order> orders = new ArrayList<>();


    @OneToMany(mappedBy = "member")
    private List<Like> likes = new ArrayList<>();


}
