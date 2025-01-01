package marcoantn020.ordersms.controller.dto;

import java.util.List;
import java.util.Map;

public record ApiResponseDto<T>(
        Map<String, Object> summary,
        List<T> data,
        PaginationResponseDto pagination) {
}
