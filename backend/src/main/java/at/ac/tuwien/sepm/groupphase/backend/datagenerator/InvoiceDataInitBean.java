package at.ac.tuwien.sepm.groupphase.backend.datagenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;

@Configuration
@Profile("datainit")
public class InvoiceDataInitBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final DataSource source;

    public InvoiceDataInitBean(DataSource source) {
        this.source = source;
    }

    @PostConstruct
    void wipeDatabase() {
        try {
            ScriptUtils.executeSqlScript(source.getConnection(), new ClassPathResource("sql/initInvoiceDb.sql"));
        } catch (Exception e) {
            LOGGER.error("Error insert data into database", e);
        }
    }
}
