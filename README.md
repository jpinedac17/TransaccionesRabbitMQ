# Sistema de Procesamiento de Transacciones con RabbitMQ

## Descripción

Este proyecto implementa una arquitectura basada en mensajería para
procesar transacciones bancarias. El sistema desacopla la obtención de
datos del procesamiento mediante el uso de colas en RabbitMQ.

La aplicación se divide en dos componentes principales: **Producer** y
**Consumer**.

------------------------------------------------------------------------

## Arquitectura

Flujo general del sistema:

API GET → Producer → RabbitMQ (colas por banco) → Consumer → API POST →
Base de datos

1.  El **Producer** obtiene transacciones desde una API externa.
2.  Cada transacción se publica en **RabbitMQ** en una cola
    correspondiente al banco destino.
3.  El **Consumer** escucha las colas y procesa los mensajes.
4.  Antes de enviarlos al endpoint final, el Consumer modifica la
    información.
5.  Finalmente la transacción se envía a un **endpoint POST** que la
    almacena en la base de datos.

------------------------------------------------------------------------

## Componentes

### Producer

Responsable de: - Consumir la API de transacciones (GET). - Convertir la
respuesta JSON a objetos Java. - Publicar cada transacción en
RabbitMQ. - Enviar cada mensaje a una cola específica según el banco.

Ejemplo de colas: - BAC - BANRURAL - BI - GYT

------------------------------------------------------------------------

### RabbitMQ

Actúa como intermediario entre Producer y Consumer.

Funciones principales: - Almacenar mensajes temporalmente. - Permitir
procesamiento asíncrono. - Garantizar entrega de mensajes mediante ACK
manual.

------------------------------------------------------------------------

### Consumer

El Consumer: 1. Escucha las colas de RabbitMQ. 2. Deserializa el JSON
recibido. 3. Modifica la transacción antes de enviarla. 4. Realiza una
petición POST al endpoint de persistencia.

Modificaciones realizadas: - Se agregan los campos **nombre** y
**carnet**. - Se modifica **idTransaccion** agregando un **UUID** para
asegurar unicidad.

------------------------------------------------------------------------

## Manejo de errores y reintentos

El Consumer implementa un modelo **at least once**:

-   Si la API responde correctamente → se confirma el mensaje (ACK).
-   Si falla → se realiza un reintento.
-   Si vuelve a fallar → el mensaje queda sin confirmar para poder
    reprocesarse.

------------------------------------------------------------------------

## Tecnologías

-   Java 11
-   RabbitMQ
-   Jackson (JSON)
-   Java HttpClient

------------------------------------------------------------------------

## Autor

Jeiner Pineda
