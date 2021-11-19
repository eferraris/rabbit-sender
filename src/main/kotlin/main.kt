import com.rabbitmq.client.ConnectionFactory

fun main() {
    val connection = connectionFactory().newConnection()
    val channel = connection.createChannel()
    val message = System.getenv("INPUT_MESSAGE")!!.toByteArray()
    val queueName = System.getenv("INPUT_RABBIT_QUEUE_NAME")
    val durable = System.getenv("INPUT_RABBIT_QUEUE_NAME")!!.toBoolean()

    try {
        channel.queueDeclare(queueName, durable, false, false, null)
        channel.basicPublish("", queueName, null, message)
    } finally {
        channel.close()
        connection.close()
    }

}

private fun connectionFactory(): ConnectionFactory {

    val factory = ConnectionFactory()
    val useSSL = System
        .getenv("INPUT_RABBIT_USE_SSL")!!
        .toBoolean()

    factory.host = System.getenv("INPUT_RABBIT_HOST")
    factory.username = System.getenv("INPUT_RABBIT_USERNAME")
    factory.password = System.getenv("INPUT_RABBIT_PASSWORD")
    if ( useSSL ) factory.useSslProtocol()
    factory.port = System.getenv("INPUT_RABBIT_PORT")!!.toInt()

    return factory

}