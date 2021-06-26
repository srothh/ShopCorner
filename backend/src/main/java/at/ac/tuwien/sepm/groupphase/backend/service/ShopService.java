package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.ShopSettings;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Service class handling business logic all around the shop.
 */
public interface ShopService {
    /**
     * Updates the shop settings.
     *
     * @param shopSettings the new shop settings that are going to overwrite the old
     * @return the updated shop settings
     * @throws IOException upon encountering problems with the settings file
     */
    ShopSettings updateSettings(ShopSettings shopSettings) throws IOException;

    /**
     * Gets the shop settings.
     *
     * @return the shop settings
     * @throws IOException upon encountering problems with the settings file
     */
    ShopSettings getSettings() throws IOException;
}
