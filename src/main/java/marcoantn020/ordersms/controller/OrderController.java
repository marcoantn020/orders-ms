package marcoantn020.ordersms.controller;

import marcoantn020.ordersms.controller.dto.ApiResponseDto;
import marcoantn020.ordersms.controller.dto.PaginationResponseDto;
import marcoantn020.ordersms.serice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponseDto<OrderResponseDto>> getAllOrders(@PathVariable Long customerId,
                                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        var totalOnOrders = orderService.findTotalOnOrdersByCustomerId(customerId);
        var pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page,pageSize)) ;
        return ResponseEntity.ok(new ApiResponseDto<>(
                Map.of("totalOnOrders", totalOnOrders),
                pageResponse.getContent(),
                PaginationResponseDto.fromPage(pageResponse)
        ));
    }


}
