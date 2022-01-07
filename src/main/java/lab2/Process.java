package lab2;

public class Process {

    private String message;
    private boolean needToHandle;

    public boolean isNeedToHandle() {
        return needToHandle;
    }

    public void setNeedToHandle(boolean needToHandle) {
        this.needToHandle = needToHandle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
