package marcoantn020.ordersms.controller;

import marcoantn020.ordersms.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderResponseDto(Long orderId,
                               Long customerId,
                               BigDecimal total) {

    public static OrderResponseDto fromEntity(OrderEntity entity) {
        return new OrderResponseDto(entity.getOrderId(),entity.getCustomerId(),entity.getTotal());
    }
}
