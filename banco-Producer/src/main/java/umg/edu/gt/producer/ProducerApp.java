package umg.edu.gt.producer;
import umg.edu.gt.producer.api.ApiClient;
import umg.edu.gt.producer.messaging.RabbitPublisher;
import umg.edu.gt.producer.model.LoteTransacciones;
import umg.edu.gt.producer.model.Transaccion;

public class ProducerApp {
    public static void main(String[] args) {

        try {
            ApiClient apiClient = new ApiClient();
            RabbitPublisher rabbit = new RabbitPublisher();

            // Obtener transacciones
            LoteTransacciones lote = apiClient.obtenerTransacciones();
            System.out.println("Total de transacciones recibidas: " + lote.getTransaccions().size());

            // Recorrer transacciones y enviarlas a Rabbitmq
            for (Transaccion transaccion : lote.getTransaccions()) {
                String banco = transaccion.getBancoDestino();
                rabbit.publicarTransaccion(banco, transaccion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en el producer");
        }
    }
}