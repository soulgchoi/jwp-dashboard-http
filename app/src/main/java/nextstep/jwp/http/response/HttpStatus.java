package nextstep.jwp.http.response;

public enum HttpStatus {

    OK("OK", 200),
    FOUND("Found", 302),
    BAD_REQUEST("Bad Request", 400),
    UNAUTHORIZED("Unauthorized", 401),
    NOT_FOUND("Not Found", 404),
    METHOD_NOT_ALLOWED("Method Not Allowed", 405),
    INTERNAL_SERVER_ERROR("Internal Server Error", 500);

    private final String message;
    private final int code;

    HttpStatus(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public boolean isRedirect() {
        return this.equals(FOUND);
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
