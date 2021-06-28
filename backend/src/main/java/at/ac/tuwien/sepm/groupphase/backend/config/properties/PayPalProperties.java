package at.ac.tuwien.sepm.groupphase.backend.config.properties;

import com.paypal.base.rest.APIContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalProperties {


    @Bean
    @ConfigurationProperties(prefix = "paypal")
    public PayPalConfig getPaypalConfig() {
        return new PayPalConfig();
    }

    @Bean
    public APIContext apiContext() {
        return new APIContext(getPaypalConfig().getClientId(), getPaypalConfig().getClientSecret(), getPaypalConfig().getMode());
    }

    protected static class PayPalConfig {
        private String clientId;
        private String clientSecret;
        private String mode;

        String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }


}
