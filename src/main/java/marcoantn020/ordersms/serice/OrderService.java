package marcoantn020.ordersms.serice;

import marcoantn020.ordersms.controller.OrderResponseDto;
import marcoantn020.ordersms.dto.OrderCreatedEventDto;
import marcoantn020.ordersms.dto.OrderItemEventDto;
import marcoantn020.ordersms.entity.OrderEntity;
import marcoantn020.ordersms.entity.OrderItem;
import marcoantn020.ordersms.repository.OrderRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(OrderCreatedEventDto event) {
        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getTotal(event));

        orderRepository.save(entity);
    }

    public Page<OrderResponseDto> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        var orders =  orderRepository.findAllByCustomerId(customerId, pageRequest);
        return orders.map(OrderResponseDto::fromEntity);
    }

    public BigDecimal findTotalOnOrdersByCustomerId(Long customerId) {
        var aggregations = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("customerId").is(customerId)),
                Aggregation.group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations,"tb_orders", Document.class);
        return new BigDecimal(response.getUniqueMappedResult().get("total").toString());
    }

    private BigDecimal getTotal(OrderCreatedEventDto event) {
        return event.items()
                .stream()
                .map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEventDto event) {
        return event.items().stream()
                .map(OrderService::getOrderItem).toList();
    }

    private static OrderItem getOrderItem(OrderItemEventDto item) {
        return new OrderItem(item.produto(), item.quantidade(), item.preco());
    }
}
