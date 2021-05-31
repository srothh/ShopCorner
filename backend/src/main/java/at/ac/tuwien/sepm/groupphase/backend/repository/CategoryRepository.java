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
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.products")
    public List<Category> getAllCategories();

    @Query(value = "SELECT c.id FROM Category c")
    public List<Long> geAllIds(Pageable page);

    @Query(value = "SELECT DISTINCT(c) FROM Category c LEFT JOIN FETCH c.products WHERE c.id IN (:ids)")
    public List<Category> findAllCategoriesPerPage(@Param("ids") List<Long> ids);
}
