package camariere;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;
import java.net.InetSocketAddress;
import java.net.URI;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

class Server {
    static final Map<String, String> types = new HashMap<>(Map.of(
            "html", "text/html",
            "js", "text/javascript",
            "css", "text/css",
            "jpg", "image/jpeg",
            "png", "image/png",
            "ico", "image/x-icon",
            "json", "application/json"
        ));

    private static int port = 8080;
    private static String prefix = "/";

    private static Logger logger = new Logger();
    // private static ConfigReader reader = new ConfigReader();

    public static void args(String[] a) {
        for (String arg : a) {
            try {
                port = Integer.parseInt(arg);
            } catch (NumberFormatException ex) {
                if (arg.indexOf('=') > 0) {
                    String pair[] = arg.split("=", 2);
                    types.putIfAbsent(pair[0], pair[1]);
                } else {
                    prefix = arg;
                    if (prefix.charAt(0) != '/') {
                        prefix = '/' + prefix;
                    }
                    if (prefix.charAt(prefix.length() - 1) != '/') {
                        prefix += '/';
                    }
                }
            }
        }
    }

    public static void main(String[] a) throws IOException {
        args(a);
        InetSocketAddress host = new InetSocketAddress("localhost", port);
        System.out.println(host.getAddress());
        HttpServer server = HttpServer.create(host, 0);
        server.createContext("/", Server::handleRequest);
        server.start();
        Message message = new Message(
                "Server is running at http://" + host.getHostName()
                + ":" + host.getPort() + prefix);
        logger.log(message);
    }

    private static void handleRequest(HttpExchange t) throws IOException {
        URI uri = t.getRequestURI();
        if (uri.toString().endsWith("/")) {
            uri = uri.resolve("index.html");
        }
        String path = uri.getPath();
        File local = null;
        if (path.startsWith(prefix)) {
            local = new File("./static", path.substring(prefix.length()));
        }
        String message = "GET " + uri;
        if (local != null && local.exists()) {
            String filename = local.getName();
            String ext = filename.substring(filename.lastIndexOf('.') + 1);
            if (types.containsKey(ext)) {
                message += " " + types.get(ext);
                t.getResponseHeaders().add("Content-Type", types.get(ext));
            }
            message += " 200 " + local.length();
            t.sendResponseHeaders(200, local.length());
            try (OutputStream out = t.getResponseBody()) {
                Files.copy(local.toPath(), out);
            }
        } else {
            message += " 404";
            String response = "File not found " + uri.toString();
            t.sendResponseHeaders(404, response.length());
            try (OutputStream os = t.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
        logger.log(new Message(message));
    }

}
