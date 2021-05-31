package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds all products that include the 'name'.
     *
     * @param name the name of the product
     * @param pageable meta data of the page
     * @return a Page of products
     * @throws RuntimeException  upon encountering errors with the database
     */
    @Query("select c from Product c where c.name like %:name%")
    Page<Product> findAllByName(@Param("name") String name, Pageable pageable);

    /**
     * Finds all products that are assigned to the 'category id'.
     *
     * @param categoryId the category id
     * @param pageable meta data of the page
     * @return a Page of products
     * @throws RuntimeException  upon encountering errors with the database
     */
    @Query("select c from Product c where c.category.id = :categoryId")
    Page<Product> findAllByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    /**
     * Finds all products that are assigned to the 'category id' and include the 'name'.
     *
     * @param name the name of the product
     * @param categoryId the category id
     * @param pageable meta data of the page
     * @return a Page of products
     * @throws RuntimeException  upon encountering errors with the database
     */
    @Query("select c from Product c where c.name like %:name% and c.category.id = :categoryId")
    Page<Product> findAllByNameAndCategoryId(@Param("name") String name, @Param("categoryId") Long categoryId, Pageable pageable);
}
