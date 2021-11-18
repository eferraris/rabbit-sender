import com.rabbitmq.client.ConnectionFactory

fun main() {
    val connection = connectionFactory().newConnection()
    val channel = connection.createChannel()
    val message = System.getenv("INPUT_MESSAGE")
        ?.toByteArray()
        ?:throw Exception("message is required")
    val queueName = System.getenv("INPUT_RABBIT_QUEUE_NAME")
        ?: throw Exception("queue name is required")

    try {
        channel.queueDeclare(queueName, false, false, false, null)
        channel.basicPublish("", queueName, null, message)
    } finally {
        channel.close()
        connection.close()
    }

}

private fun connectionFactory(): ConnectionFactory {

    val factory = ConnectionFactory()

    factory.host = System.getenv("INPUT_RABBIT_HOST")?: "localhost"
    factory.username = System.getenv("INPUT_RABBIT_USERNAME")?: throw Exception("username must be specified")
    factory.password = System.getenv("INPUT_RABBIT_PASSWORD")?: ""
    System.getenv("INPUT_RABBIT_USE_SSL")?.let { if ( it.toBoolean() ) factory.useSslProtocol() }
    factory.port = System.getenv("INPUT_RABBIT_PORT")?.toInt()?: 5671

    return factory

}