package minishop.project.api.response;


import lombok.Getter;
import lombok.Setter;
import minishop.project.domain.member.entity.Member;

@Getter
@Setter
public class FindOrderByMemberResult extends Result{

//    private Member member;
    private String loginEmail;

    public FindOrderByMemberResult(Object data, int cnt, String loginEmail) {
        super(data, cnt);
        this.loginEmail = loginEmail;
    }

    public FindOrderByMemberResult(String loginEmail) {
        this.loginEmail = loginEmail;
    }
    public FindOrderByMemberResult() {}
}
