package com.consumer.messaging;

import com.consumer.api.ApiClient;
import com.consumer.model.Transaccion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.net.http.HttpResponse;
import java.util.UUID;

public class RabbitConsumer {
    private final Channel channel;
    private final ObjectMapper objectMapper;
    private final ApiClient apiClient;

    public RabbitConsumer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();
        this.objectMapper = new ObjectMapper();
        this.apiClient = new ApiClient();
    }

    public void escucharCola(String cola) throws Exception {
        channel.queueDeclare(cola, true, false, false, null);
        System.out.println("Escuchando cola: " +  cola);

        channel.basicConsume(
                cola,
                false,  //autoACK desactivado
                (consumerTag, delivery) -> {
                    String mensaje = new String(delivery.getBody());
                    //System.out.println("Mensaje recibido: " + mensaje);

                    try {

                        Transaccion transaccion =
                                objectMapper.readValue(mensaje, Transaccion.class);

                        // Agregar datos nuevos:
                        transaccion.setNombre("Jeiner Andy Josué Pineda Corleto");
                        transaccion.setCarnet("0905-24-1925");

                        // Modificar idTransaccion
                        String nuevoId = transaccion.getIdTransaccion() + "-" + UUID.randomUUID();
                        transaccion.setIdTransaccion(nuevoId);
                        
                        if (cola.equals("cola_rechazados")) {
                        	System.out.println("Transaccion rechazada: " + transaccion.getIdTransaccion() + " monto: " + transaccion.getMonto());
                        } else {
                        	// Enviar a API por post
                            HttpResponse<String> response =
                                    apiClient.enviarTransaccion(transaccion);

                            if (response.statusCode() == 201) {

                                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                                System.out.println("Transaccion guardada: " + transaccion.getIdTransaccion() + " monto: " + transaccion.getMonto());

                            } else {

                                System.out.println("Primer intento falló, reintentando...");

                                HttpResponse<String> retry =
                                        apiClient.enviarTransaccion(transaccion);

                                if (retry.statusCode() == 201) {

                                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                                    System.out.println("Transaccion guardada en reintento");

                                } else {

                                    System.out.println("Falló incluso en reintento");

                                }
                            }
                        }

                    } catch (Exception e) {

                        System.out.println("Error procesando mensaje: " + e.getMessage());

                    }
                },
                consumerTag -> {}
        );
    }
}
