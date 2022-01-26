package lab2;

import java.util.StringJoiner;

public class Process {

    private String message;
    private boolean sent;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Process.class.getSimpleName() + "[", "]").add("message='" + message + "'").toString();
    }
}
