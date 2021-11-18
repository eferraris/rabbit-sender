# Rabbit Sender Action

This Action helps you to send a message to a queue on a RabbitMQ running Server.

## Inputs
| Arg | Default | Description |
| --- | --- | --- |
| RABBIT_USERNAME | guest | RabbitMQ Username |
| RABBIT_PASSWORD | guest | RabbitMQ Password  |
| RABBIT_HOST | localhost | RabbitMQ Host |
| RABBIT_PORT | 5672 | RabbitMQ Port |
| RABBIT_USE_SSL | false | If the RabbitMQ uses SSL |
| RABBIT_QUEUE_NAME | rabbit-sender | Name of the queue the message must arrive |
| MESSAGE | Hello World! | Message you want to send |

## Usage
```yaml
- name: Publish message to RabbitMQ
  uses: eferraris/rabbit-sender@v1
  with:
    MESSAGE: "Test message"
    RABBIT_QUEUE_NAME: "test-rabbit-queue"
    RABBIT_HOST: ${{ secrets.RABBIT_HOST }}
    RABBIT_USERNAME: ${{ secrets.RABBIT_USERNAME }}
    RABBIT_PASSWORD: ${{ secrets.RABBIT_PASSWORD }}
    RABBIT_PORT: 5671
    RABBIT_USE_SSL: true
```
