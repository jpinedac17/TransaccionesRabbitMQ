package com.consumer;

import com.consumer.messaging.RabbitConsumer;

public class ConsumerApp {
    public static void main(String[] args) {

    	//Rama: ExamenParcial
        try {
            RabbitConsumer consumer = new RabbitConsumer();

            consumer.escucharCola("BAC");
            consumer.escucharCola("BANRURAL");
            consumer.escucharCola("BI");
            consumer.escucharCola("GYT");
        } catch (Exception e) {
            System.out.println("Error iniciando consumer");
            e.printStackTrace();
        }
    }
}
