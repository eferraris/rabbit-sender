import com.rabbitmq.client.ConnectionFactory

fun main() {
    val connection = connectionFactory().newConnection()
    val channel = connection.createChannel()
    val message = System.getenv("INPUT_MESSAGE").takeIf { it.isNotEmpty() }
        ?.toByteArray()
        ?:"Hello World!".toByteArray()
    val queueName = System.getenv("INPUT_RABBIT_QUEUE_NAME").takeIf { it.isNotEmpty() }?: "rabbit-sender"
    val exchangeName = System.getenv("INPUT_RABBIT_EXCHANGE_NAME").takeIf { it.isNotEmpty() }?: ""
    val durable = System.getenv("INPUT_DURABLE").takeIf { it.isNotEmpty() }?.toBoolean()?: true

    try {
        if ( exchangeName.isNotEmpty() ) {
            channel.exchangeDeclare(exchangeName, "topic", durable)
            channel.basicPublish(exchangeName, "*", null, message)
        } else {
            channel.queueDeclare(queueName, durable, false, false, null)
            channel.basicPublish("", queueName, null, message)
        }

    } finally {
        channel.close()
        connection.close()
    }

}

private fun connectionFactory(): ConnectionFactory {

    val factory = ConnectionFactory()
    val useSSL = System
        .getenv("INPUT_RABBIT_USE_SSL")
        .takeIf { it.isNotEmpty() }
        ?.toBoolean()
        ?: true

    factory.host = System.getenv("INPUT_RABBIT_HOST")?: "localhost"
    factory.username = System.getenv("INPUT_RABBIT_USERNAME").takeIf { it.isNotEmpty() }?: "guest"
    factory.password = System.getenv("INPUT_RABBIT_PASSWORD").takeIf { it.isNotEmpty() }?: "guest"
    if ( useSSL ) factory.useSslProtocol()
    factory.port = System.getenv("INPUT_RABBIT_PORT").takeIf { it.isNotEmpty() }?.toInt() ?: 5672

    return factory

}