package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class PaginationRequestDto {
    private int page = 0;
    private int pageCount = 15;

    public PaginationRequestDto() { }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPage_Count(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
