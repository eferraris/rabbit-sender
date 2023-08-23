import com.rabbitmq.client.ConnectionFactory

fun main() {
    println("INPUT_MESSAGE: ${getEnvOrNull("INPUT_MESSAGE")}")
    println("INPUT_ROUTING_KEY: ${getEnvOrNull("INPUT_ROUTING_KEY")}")
    println("INPUT_RABBIT_QUEUE_NAME: ${getEnvOrNull("INPUT_RABBIT_QUEUE_NAME")}")
    println("INPUT_RABBIT_EXCHANGE_NAME: ${getEnvOrNull("INPUT_RABBIT_EXCHANGE_NAME")}")
    println("INPUT_DURABLE: ${getEnvOrNull("INPUT_DURABLE")}")
    println("INPUT_RABBIT_USE_SSL: ${getEnvOrNull("INPUT_RABBIT_USE_SSL")}")
    println("INPUT_RABBIT_HOST: ${getEnvOrNull("INPUT_RABBIT_HOST")}")
    println("INPUT_RABBIT_USERNAME: ${getEnvOrNull("INPUT_RABBIT_USERNAME")}")
    println("INPUT_RABBIT_PASSWORD: ${getEnvOrNull("INPUT_RABBIT_PASSWORD")}")
    println("INPUT_RABBIT_PORT: ${getEnvOrNull("INPUT_RABBIT_PORT")}")

    val connection = connectionFactory().newConnection()
    val channel = connection.createChannel()
    val message = getEnvOrNull("INPUT_MESSAGE")
        ?.toByteArray()
        ?:"Hello World!".toByteArray()
    val routingKey = getEnvOrNull("INPUT_ROUTING_KEY")?: "*"
    val queueName = getEnvOrNull("INPUT_RABBIT_QUEUE_NAME")?: "rabbit-sender"
    val exchangeName = getEnvOrNull("INPUT_RABBIT_EXCHANGE_NAME")?: ""
    val durable = getEnvOrNull("INPUT_DURABLE")?.toBoolean()?: true

    try {
        if ( exchangeName.isNotEmpty() ) {
            channel.exchangeDeclare(exchangeName, "topic", durable)
            channel.basicPublish(exchangeName, routingKey, null, message)
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
    val useSSL = getEnvOrNull("INPUT_RABBIT_USE_SSL")
        ?.toBoolean()
        ?: true

    factory.host = getEnvOrNull("INPUT_RABBIT_HOST")?: "localhost"
    factory.username = getEnvOrNull("INPUT_RABBIT_USERNAME")?: "guest"
    factory.password = getEnvOrNull("INPUT_RABBIT_PASSWORD")?: "guest"
    if ( useSSL ) factory.useSslProtocol()
    factory.port = getEnvOrNull("INPUT_RABBIT_PORT")?.toInt() ?: 5672

    return factory

}
private fun getEnvOrNull(name: String): String? = System
    .getenv( name )
    ?.let { it.takeIf { it.isNotEmpty() } }