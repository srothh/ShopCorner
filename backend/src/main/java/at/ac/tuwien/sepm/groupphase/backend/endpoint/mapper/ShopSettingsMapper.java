package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShopSettingsDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ShopSettings;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ShopSettingsMapper {
    ShopSettingsDto entityToDto(ShopSettings shopSettings);

    ShopSettings dtoToEntity(ShopSettingsDto shopSettingsDto);
}
