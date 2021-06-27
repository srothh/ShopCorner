package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ShopSettingsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ShopSettingsMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.ShopSettings;
import at.ac.tuwien.sepm.groupphase.backend.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

@RestController
public class ShopEndpoint {
    private static final String BASE_URL = "/api/v1/shop";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ShopService shopService;
    private final ShopSettingsMapper shopSettingsMapper;

    @Autowired
    public ShopEndpoint(ShopService shopService, ShopSettingsMapper shopSettingsMapper) {
        this.shopService = shopService;
        this.shopSettingsMapper = shopSettingsMapper;
    }

    /**
     * Sets the shop settings.
     *
     * @param shopSettingsDto the dto containing the settings
     * @return updated shop settings
     * @throws IOException upon encountering problems with the settings file
     */
    @Secured("ROLE_ADMIN")
    @PutMapping(BASE_URL + "/settings")
    @ResponseStatus(HttpStatus.OK)
    public ShopSettings updateSettings(@Valid @RequestBody ShopSettingsDto shopSettingsDto) throws IOException {
        LOGGER.info("PUT " + BASE_URL + "/settings {}", shopSettingsDto);
        ShopSettings shopSettings = shopSettingsMapper.dtoToEntity(shopSettingsDto);
        return this.shopService.updateSettings(shopSettings);
    }

    /**
     * Sets the shop settings.
     *
     * @return shop settings
     * @throws IOException upon encountering problems with the settings file
     */
    @PermitAll
    @GetMapping(BASE_URL + "/settings")
    @ResponseStatus(HttpStatus.OK)
    public ShopSettingsDto getSettings() throws IOException {
        LOGGER.info("GET " + BASE_URL + "/settings");
        ShopSettings shopSettings = this.shopService.getSettings();
        return shopSettingsMapper.entityToDto(shopSettings);
    }
}
