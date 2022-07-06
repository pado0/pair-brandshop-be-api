package minishop.project.api.response;

import lombok.Getter;
import lombok.Setter;

// todo : 반환값 예쁘게 만들기
@Getter
@Setter
public class CommonResult {

    private boolean success;

    private int code;

    private String msg;
}

