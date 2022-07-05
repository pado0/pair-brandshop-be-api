package minishop.project.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotEnoughStockException extends RuntimeException{

    public NotEnoughStockException(String message) {
        super(message);
    }
//    쓰임새 체크 필요
    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
