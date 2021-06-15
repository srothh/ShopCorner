package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PromotionDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Promotion;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PromotionMapper {
    Promotion promotionDtoToPromotion(PromotionDto dto);

    PromotionDto promotionToPromotionDto(Promotion promotion);

    List<PromotionDto> promotionListToPromotionDtoList(List<Promotion> promotions);
}

