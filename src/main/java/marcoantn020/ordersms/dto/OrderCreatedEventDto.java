package marcoantn020.ordersms.dto;

import java.util.List;

public record OrderCreatedEventDto(Long codigoPedido,
                                   Long codigoCliente,
                                   List<OrderItemEventDto> items) {
}
