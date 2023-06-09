# Rabbit Sender Action

This Action helps you to send a message to a queue on a RabbitMQ running Server.

## Inputs
| Arg                  | Default       | Description                                                                                         |
|----------------------|---------------|-----------------------------------------------------------------------------------------------------|
| RABBIT_USERNAME      | guest         | RabbitMQ Username                                                                                   |
| RABBIT_PASSWORD      | guest         | RabbitMQ Password                                                                                   |
| RABBIT_HOST          | localhost     | RabbitMQ Host                                                                                       |
| RABBIT_PORT          | 5672          | RabbitMQ Port                                                                                       |
| RABBIT_USE_SSL       | true          | If the RabbitMQ uses SSL                                                                            |
| RABBIT_QUEUE_NAME    | rabbit-sender | Name of the queue the message must arrive                                                           |
| RABBIT_EXCHANGE_NAME | -             | Name of the exchange the message must arrive. If exchange name is set, RABBIT_QUEUE_NAME is ignored |
| MESSAGE              | Hello World!  | Message you want to send                                                                            |
| DURABLE              | true          | Queue durability                                                                                    |

## Usage
```yaml
- name: Publish message to queue
  uses: eferraris/rabbit-sender@v1
  with:
    MESSAGE: "Test message"
    RABBIT_QUEUE_NAME: "test-rabbit-queue"
    RABBIT_HOST: ${{ secrets.RABBIT_HOST }}
    RABBIT_USERNAME: ${{ secrets.RABBIT_USERNAME }}
    RABBIT_PASSWORD: ${{ secrets.RABBIT_PASSWORD }}
    RABBIT_PORT: 5671
    RABBIT_USE_SSL: true
    DURABLE: false
```

```yaml
- name: Publish message to exchange
  uses: eferraris/rabbit-sender@v1
  with:
    MESSAGE: "Test message"
    RABBIT_EXCHANGE_NAME: "test-rabbit-exchange"
    RABBIT_HOST: ${{ secrets.RABBIT_HOST }}
    RABBIT_USERNAME: ${{ secrets.RABBIT_USERNAME }}
    RABBIT_PASSWORD: ${{ secrets.RABBIT_PASSWORD }}
    RABBIT_PORT: 5671
    RABBIT_USE_SSL: true
    DURABLE: false
```
