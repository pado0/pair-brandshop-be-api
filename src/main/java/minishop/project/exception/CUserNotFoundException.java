package minishop.project.exception;

public class CUserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "존재하지 않는 사용자입니다";
    public CUserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CUserNotFoundException(String msg) {
        super(msg);
    }

    public CUserNotFoundException() {
        super(MESSAGE);
    }
}
