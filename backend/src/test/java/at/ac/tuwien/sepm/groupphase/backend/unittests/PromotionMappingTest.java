package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PromotionDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PromotionMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class PromotionMappingTest implements TestData {

    private final Promotion promotion = new Promotion(0L, TEST_PROMOTION_NAME, TEST_PROMOTION_DISCOUNT, LocalDateTime.now(), TEST_PROMOTION_EXPIRATIONDATE, TEST_PROMOTION_CODE, TEST_PROMOTION_MINIMUMORDERVALUE);
    @Autowired
    PromotionMapper promotionMapper;

    @Test
    void givenNothing_whenMapPromotionToPromotionDto_thenEntityHasAllProperties() {
        PromotionDto promotionDto = promotionMapper.promotionToPromotionDto(promotion);
        assertAll(
            () -> assertEquals(0, promotionDto.getId()),
            () -> assertEquals(TEST_PROMOTION_NAME, promotionDto.getName()),
            () -> assertEquals(TEST_PROMOTION_DISCOUNT, promotionDto.getDiscount()),
            () -> assertEquals(TEST_PROMOTION_EXPIRATIONDATE, promotionDto.getExpirationDate()),
            () -> assertEquals(TEST_PROMOTION_CODE, promotionDto.getCode()),
            () -> assertEquals(TEST_PROMOTION_MINIMUMORDERVALUE, promotionDto.getMinimumOrderValue())
        );
    }

    @Test
    void givenNothing_whenMapListWithTwoPromotionEntitiesToDto_thenGetListWithSizeTwoAndAllProperties() {
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(promotion);
        promotions.add(promotion);
        List<PromotionDto> promotionDtos = promotionMapper.promotionListToPromotionDtoList(promotions);
        assertEquals(2, promotionDtos.size());
        PromotionDto promotionDto = promotionDtos.get(0);
        assertAll(
            () -> assertEquals(0, promotionDto.getId()),
            () -> assertEquals(TEST_PROMOTION_NAME, promotionDto.getName()),
            () -> assertEquals(TEST_PROMOTION_DISCOUNT, promotionDto.getDiscount()),
            () -> assertEquals(TEST_PROMOTION_EXPIRATIONDATE, promotionDto.getExpirationDate()),
            () -> assertEquals(TEST_PROMOTION_CODE, promotionDto.getCode()),
            () -> assertEquals(TEST_PROMOTION_MINIMUMORDERVALUE, promotionDto.getMinimumOrderValue())
        );
    }
}
