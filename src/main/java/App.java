import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", 8080), 0
        );

        server.createContext("/", exchange -> {
            String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <title>My Java Web App</title>
                    <style>
                        body {
                            margin: 0;
                            font-family: Arial, sans-serif;
                            background: #f1f5f9;
                            color: #172554;
                            text-align: center;
                        }
                        header {
                            background: #0f172a;
                            color: white;
                            padding: 22px;
                        }
                        main {
                            max-width: 760px;
                            margin: 70px auto;
                            padding: 30px;
                        }
                        .card {
                            background: white;
                            padding: 30px;
                            border-radius: 16px;
                            box-shadow: 0 8px 28px #00000012;
                        }
                        .status {
                            color: #15803d;
                            font-weight: bold;
                        }
                        .button {
                            display: inline-block;
                            margin-top: 18px;
                            padding: 12px 22px;
                            background: #2563eb;
                            color: white;
                            text-decoration: none;
                            border-radius: 8px;
                        }
                        footer {
                            margin-top: 35px;
                            color: #64748b;
                        }
                    </style>
                </head>
                <body>
                    <header><h2>Java CI/CD Demo</h2></header>
                    <main>
                        <div class="card">
                            <p class="status">Application is running</p>
                            <h1>Welcome to My Java Web App!</h1>
                            <p>This website is powered by Java.</p>
                            <p>Built with Maven, Docker and Kubernetes.</p>
                            <a class="button" href="/health">Check App Health</a>
                        </div>
                        <footer>Deployed locally with Kubernetes</footer>
                    </main>
                </body>
                </html>
                """;

            byte[] response = html.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8"
            );
            exchange.sendResponseHeaders(200, response.length);

            try (var output = exchange.getResponseBody()) {
                output.write(response);
            }
        });

        server.createContext("/health", exchange -> {
            byte[] response = "OK".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=UTF-8"
            );
            exchange.sendResponseHeaders(200, response.length);

            try (var output = exchange.getResponseBody()) {
                output.write(response);
            }
        });

        server.start();
        System.out.println("Website running on port 8080");
    }
}
