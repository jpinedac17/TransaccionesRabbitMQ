package umg.edu.gt.producer.messaging;

import com.rabbitmq.client.MessageProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import umg.edu.gt.producer.model.Transaccion;

public class RabbitPublisher {

    private final Connection connection;
    private final Channel channel;
    private final ObjectMapper objectMapper;

    public RabbitPublisher() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        this.objectMapper = new ObjectMapper();
    }

    public  void publicarTransaccion(String cola, Transaccion transaccion) throws Exception {
        // Crear la cola si no existe
        channel.queueDeclare(cola, true, false, false, null);

        // Convertir el objeto a json
        String mensaje = objectMapper.writeValueAsString(transaccion);

        // Enviar mensaje a Rabbitmq
        channel.basicPublish("", cola, MessageProperties.PERSISTENT_TEXT_PLAIN, mensaje.getBytes());
        System.out.println("Enviando transaccion " + transaccion.getIdTransaccion() + " a cola: " + cola);
    }
}
