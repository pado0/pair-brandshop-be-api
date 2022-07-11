package minishop.project.api.response;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result <T>{

    public Result(T data, int cnt) {
        this.data = data;
        this.cnt = cnt;
    }

    public Result() {
    }

    private T data;
    int cnt;
}
