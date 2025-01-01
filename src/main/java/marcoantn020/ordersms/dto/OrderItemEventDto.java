package marcoantn020.ordersms.dto;

import java.math.BigDecimal;

public record OrderItemEventDto(String produto,
                                Integer quantidade,
                                BigDecimal preco) {
}
