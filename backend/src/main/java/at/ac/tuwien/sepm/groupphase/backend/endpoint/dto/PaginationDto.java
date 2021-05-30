package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.util.List;

public class PaginationDto<T> {
    private List<T> items;
    private int pageNumber;
    private int pageSize;
    private Long totalItemCount;

    public PaginationDto() {
    }


    public PaginationDto(List<T> items, int pageNumber, int pageSize, Long totalItemCount) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalItemCount = totalItemCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(Long totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
