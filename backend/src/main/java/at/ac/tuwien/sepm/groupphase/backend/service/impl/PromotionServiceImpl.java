package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import at.ac.tuwien.sepm.groupphase.backend.repository.PromotionRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class PromotionServiceImpl implements PromotionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final PromotionRepository promotionRepository;


    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Caching(evict = {
        @CacheEvict(value = "counts", key = "'promotions'"),
        @CacheEvict(value = "promotionPages", allEntries = true)
    })
    @Override
    public Promotion addNewPromotion(Promotion promotion) {
        LOGGER.trace("addNewPromotion({})", promotion);
        return this.promotionRepository.save(promotion);
    }

    @Cacheable(value = "promotionPages")
    @Override
    public Page<Promotion> getAllPromotions(int page, int pageCount) {
        LOGGER.trace("getAllPromotions()");
        if (pageCount == 0) {
            pageCount = 15;
        } else if (pageCount > 50) {
            pageCount = 50;
        }
        Pageable returnPage = PageRequest.of(page, pageCount);
        return promotionRepository.findAll(returnPage);
    }

    @Cacheable(value = "counts", key = "'promotions'")
    @Override
    public Long getPromotionCount() {
        return promotionRepository.count();
    }


}
