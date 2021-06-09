package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionService {

    Promotion addNewPromotion(Promotion promotion);

    Page<Promotion> getAllPromotions(int page, int pageCount);

    Long getPromotionCount();

    List<Promotion> findAll();
}
