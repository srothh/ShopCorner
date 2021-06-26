package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.ShopSettings;
import at.ac.tuwien.sepm.groupphase.backend.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

@Service
public class ShopServiceImpl implements ShopService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final String settingsPath = "src/main/resources/shop.settings";
    private final Properties properties = new Properties();

    @Override
    public ShopSettings updateSettings(ShopSettings shopSettings) throws IOException {
        LOGGER.trace("updateSettings({})", shopSettings);
        File f = new File(settingsPath);
        OutputStream out = new FileOutputStream(f);
        shopSettings.setProperties(properties);
        properties.store(out, "ShopSettings");
        out.close();
        return shopSettings;
    }

    @Override
    public ShopSettings getSettings() throws IOException {
        LOGGER.trace("getSettings()");
        File f = new File(settingsPath);
        InputStream in;
        try {
            in = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            ShopSettings shopSettings = ShopSettings.ShopSettingsBuilder.getShopSettingsBuilder()
                .withTitle("ShopCorner")
                .withLogo("https://i.imgur.com/zMBx1FY.png")
                .withBannerTitle("ShopCorner")
                .withBannerText("Willkommen bei ShopCorner!")
                .build();
            this.updateSettings(shopSettings);
            in = new FileInputStream(f);
        }
        properties.load(in);
        ShopSettings shopSettings = ShopSettings.buildFromProperties(properties);
        in.close();
        return shopSettings;
    }
}
