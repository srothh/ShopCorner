package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.List;

public class PaginationDto<T> {
    private List<T> items;
    private int pageNumber;
    private int pageSize;
    private Long totalItemCount;

    public PaginationDto() {
    }

    ;

    public PaginationDto(List<T> items, int pageNumber, int pageSize, Long totalItemCount) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalItemCount = totalItemCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
