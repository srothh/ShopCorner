package at.ac.tuwien.sepm.groupphase.backend.util;

import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public ProductSpecifications() {
    }

    /**
     * returns specification that searches for given deleted status.
     *
     * @param deleted that need to be searched for
     * @return specification (includes root, criteriaQuery and criteriaBuilder)
     */
    public static Specification<Product> hasDeleted(boolean deleted) {
        return (product, cq, cb) -> cb.equal(product.get("deleted"), deleted);
    }

    /**
     * returns specification that searches for given category.
     *
     * @param categoryId that need to be searched for
     * @return specification (includes root, criteriaQuery and criteriaBuilder)
     */
    public static Specification<Product> isCategory(Long categoryId) {
        return (product, cq, cb) -> cb.equal(product.get("category"), categoryId);
    }

    /**
     * returns specification that searches for given deleted status.
     *
     * @param name that need to be searched for
     * @return specification (includes root, criteriaQuery and criteriaBuilder)
     */
    public static Specification<Product> hasName(String name) {
        return (product, cq, cb) -> cb.like(product.get("name"), "%" + name + "%");
    }
}
