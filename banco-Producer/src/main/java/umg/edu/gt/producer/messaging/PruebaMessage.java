package umg.edu.gt.producer.messaging;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class PruebaMessage {
    private final static String QUEUE_NAME = "colaBanco";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = "Este es un mensaje de prueba desde Java";

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println("Mensaje enviado: " + message);
        }
    }
}
