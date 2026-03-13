package umg.edu.gt.producer.api;
import umg.edu.gt.producer.model.LoteTransacciones;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private static final String URL = "https://hly784ig9d.execute-api.us-east-1.amazonaws.com/default/transacciones";

    private final HttpClient client;
    private final ObjectMapper mapper;

    public ApiClient() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public LoteTransacciones obtenerTransacciones() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error en GET: " + response.statusCode());
        }

        return mapper.readValue(response.body(), LoteTransacciones.class);
    }
}
