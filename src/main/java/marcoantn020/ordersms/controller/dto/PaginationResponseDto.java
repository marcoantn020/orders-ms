package marcoantn020.ordersms.controller.dto;

import org.springframework.data.domain.Page;

public record PaginationResponseDto(Integer page,
                                    Integer pageSize,
                                    Long totalElements,
                                    Integer totalPages) {

    public static PaginationResponseDto fromPage(Page<?> page) {
        return new PaginationResponseDto(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
