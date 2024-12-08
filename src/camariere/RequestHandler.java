package camariere;

class RequestHandler {

    public RequestHandler() {}

    public HttpMethod getHttpMethod() {
        return null;
    }

    // Goal here is to maintain a standard means of mapping http requests to
    // structured definitions.
    private enum HttpMethod {
        GET("get"),
        PUT("put"),
        POST("post"),
        FETCH("fetch"),
        HEAD("head"),
        DELETE("delete"),
        OPTIONS("options"),
        CONNECT("connect"),
        TRACE("trace"),
        PATCH("patch");

        private final String method;

        HttpMethod(String methodName) {
            this.method = methodName;
        }

    }
}
