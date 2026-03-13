package com.consumer.api;

import com.consumer.model.Transaccion;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ApiClient {

    public HttpResponse<String> enviarTransaccion(Transaccion transaccion) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        HttpClient httpClient = HttpClient.newHttpClient();

        String json = mapper.writeValueAsString(transaccion);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://7e0d9ogwzd.execute-api.us-east-1.amazonaws.com/default/guardarTransacciones"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
