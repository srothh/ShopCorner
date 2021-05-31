package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Gets all categories and their related products (which are fetched lazily).
     *
     * @return all categories
     */
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.products")
    public List<Category> getAllCategories();

    /**
     * Gets all ids specified by a page-able object that specified the page-request.
     *
     * @return a list of ids specified by the page-able object
     */
    @Query(value = "SELECT c.id FROM Category c")
    public List<Long> geAllIds(Pageable page);

    /**
     * Gets all categories specified by the ids.
     *
     * @param ids a list of category-ids that specified which exact categories are needed
     *
     * @return a list containing all categories specified by the ids
     */
    @Query(value = "SELECT DISTINCT(c) FROM Category c LEFT JOIN FETCH c.products WHERE c.id IN (:ids)")
    public List<Category> findAllCategoriesPerPage(@Param("ids") List<Long> ids);
}
