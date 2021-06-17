package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class ProductSearchDto {
    private long categoryId = -1;
    private String name = "";
    private String sortBy = "id";

    ProductSearchDto() { }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public String toString() {
        return "ProductSearchDto{" + "categoryId=" + categoryId + ", name='" + name + '\'' + '}';
    }
}
