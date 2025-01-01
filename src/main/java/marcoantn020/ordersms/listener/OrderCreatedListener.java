package marcoantn020.ordersms.listener;

import marcoantn020.ordersms.dto.OrderCreatedEventDto;
import marcoantn020.ordersms.serice.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static marcoantn020.ordersms.config.RabbitMqConfig.ORDER_CREATED_QUEUE;

@Component
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEventDto> message) {
        logger.info("Message consumed: {}", message);
        orderService.save(message.getPayload());
    }
}
