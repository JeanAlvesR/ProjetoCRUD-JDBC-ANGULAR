package modelo.controladores;

import com.sun.net.httpserver.HttpServer;
import modelo.servico.ProdutoServico;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.google.gson.Gson;

public class ProdutoControler {

    public static void rest() throws IOException {

        ProdutoServico produtoServico = new ProdutoServico();
        int serverPort = 4200;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        server.createContext("/produtos", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                Gson g = new Gson();
                String produtoJson = g.toJson(produtoServico.executarBuscarTodos());
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, produtoJson.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(produtoJson.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        });
        server.setExecutor(null);
        server.start();
    }
}
