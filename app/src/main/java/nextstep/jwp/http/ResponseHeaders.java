package nextstep.jwp.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHeaders {

    Map<String, String> headers = new LinkedHashMap<>();

    public ResponseHeaders() {
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeaders(String key, String value) {
        headers.put(key, value);
    }
}
