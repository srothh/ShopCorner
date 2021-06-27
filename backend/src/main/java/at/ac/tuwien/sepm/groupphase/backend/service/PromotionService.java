package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import at.ac.tuwien.sepm.groupphase.backend.exception.ValidationException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionService {

    /**
     * Add a new promotion to the database.
     *
     * @param promotion the promotion to add
     * @return the promotion added
     * @throws RuntimeException    upon encountering errors with the database
     * @throws ValidationException when the promotion is invalid
     */
    Promotion addNewPromotion(Promotion promotion);

    /**
     * Returns a page of promotions from the database.
     *
     * @param page      the page number to retrieve from the database
     * @param pageCount the size of the page
     * @return the page as retrieved from the database
     * @throws RuntimeException upon encountering errors with the database
     */
    Page<Promotion> getAllPromotions(int page, int pageCount);

    /**
     * Gets amount of promotions in the database.
     *
     * @return the amount of promotions persisted in the database
     * @throws RuntimeException upon encountering errors with the database
     */
    Long getPromotionCount();

    /**
     * Return all promotions from the database.
     *
     * @return all promotions found in the database
     * @throws RuntimeException upon encountering errors with the database
     */
    List<Promotion> findAll();

    /**
     * Finds a promotion based on its code.
     *
     * @param code THe code of the promotion
     * @return The promotion as found in the datastore
     */
    Promotion findPromotionByCode(String code);
}
