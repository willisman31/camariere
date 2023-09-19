package camariere;

class Server
{
  static final java.util.Map<String, String> types =
      new java.util.HashMap<>(java.util.Map.of(
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
  
  public static void args(String[] a)
  {
    for(String arg : a)
    {
      try
      {
        port = Integer.parseInt(arg);
      }
      catch(NumberFormatException ex)
      {
        if(arg.indexOf('=') > 0)
        {
          String pair[] = arg.split("=", 2);
          types.putIfAbsent(pair[0], pair[1]);
        }
        else
        {
          prefix = arg;
          if(prefix.charAt(0) != '/')
          {
            prefix = '/' + prefix;
          }
          if(prefix.charAt(prefix.length() - 1) != '/')
          {
            prefix += '/';
          }
        }
      }
    }
  }

  public static void main(String[] a) throws java.io.IOException
  {
    args(a);
    java.net.InetSocketAddress host =
        new java.net.InetSocketAddress("localhost", port);
    com.sun.net.httpserver.HttpServer server =
        com.sun.net.httpserver.HttpServer.create(host, 0
        );
    server.createContext("/", Server::handleRequest);
    server.start();
    logger.log("Server is running at http://"
        + host.getHostName() + ":" + host.getPort() + prefix);
  }

  private static void handleRequest(com.sun.net.httpserver.HttpExchange t)
      throws java.io.IOException
  {
    java.net.URI uri = t.getRequestURI();
    if(uri.toString().endsWith("/"))
    {
      uri = uri.resolve("index.html");
    }
    String path = uri.getPath();
    java.io.File local = null;
    if(path.startsWith(prefix))
    {
      local = new java.io.File("./static", path.substring(prefix.length()));
    }
    String message = new java.util.Date().toString() + " GET " + uri;
    if(local != null && local.exists())
    {
      //String response = "This is the response of "+local.getAbsolutePath();
      String filename = local.getName();
      String ext = filename.substring(filename.lastIndexOf('.') + 1);
      if(types.containsKey(ext))
      {
        message += " " + types.get(ext);
        t.getResponseHeaders()
            .add("Content-Type", types.get(ext));
      }
      message += " 200 " + local.length();
      t.sendResponseHeaders(200, local.length());
      try(java.io.OutputStream out = t.getResponseBody())
      {
        java.nio.file.Files.copy(local.toPath(), out);
      }
    }
    else
    {
      message += " 404";
      String response = "File not found " + uri.toString();
      t.sendResponseHeaders(404, response.length());
      try(java.io.OutputStream os = t.getResponseBody())
      {
        os.write(response.getBytes());
      }
    }
    logger.log(message);
  }
}
