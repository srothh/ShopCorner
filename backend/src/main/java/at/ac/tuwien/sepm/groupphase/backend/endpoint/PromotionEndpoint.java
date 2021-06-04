package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PromotionDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PromotionMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(PromotionEndpoint.BASE_URL)
public class PromotionEndpoint {
    static final String BASE_URL = "/api/v1/promotions";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final PromotionMapper promotionMapper;
    private final PromotionService promotionService;

    @Autowired
    public PromotionEndpoint(PromotionMapper promotionMapper, PromotionService promotionService) {
        this.promotionMapper = promotionMapper;
        this.promotionService = promotionService;
    }

    @PostMapping
    @PermitAll
    public PromotionDto addPromotion(@Valid @RequestBody PromotionDto dto) {
        LOGGER.info("POST " + BASE_URL);
        return promotionMapper.promotionToPromotionDto(promotionService.addNewPromotion(promotionMapper.promotionDtoToPromotion(dto)));
    }

}
