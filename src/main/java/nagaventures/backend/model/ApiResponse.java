package nagaventures.backend.model;

public class ApiResponse {

    private int statusCode;
    private Object data;
    private Object userId;
    private String message;

    public ApiResponse(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ApiResponse( int statusCode, Object userId, String message, Object data) {
        this.statusCode = statusCode;
        this.userId = userId;
        this.data = data;
        this.message = message;
    }

    // Getters and Setters


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
