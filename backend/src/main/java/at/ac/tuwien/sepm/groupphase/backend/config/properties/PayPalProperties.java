package at.ac.tuwien.sepm.groupphase.backend.config.properties;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

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

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }


}
