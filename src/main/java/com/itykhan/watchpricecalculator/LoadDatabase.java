package com.itykhan.watchpricecalculator;

import com.itykhan.watchpricecalculator.data.entity.Discount;
import com.itykhan.watchpricecalculator.data.entity.Watch;
import com.itykhan.watchpricecalculator.data.reporitory.WatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(WatchRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(
                    new Watch("001", "Rolex", new BigDecimal(100),
                            new Discount(3, new BigDecimal(200)))));
            log.info("Preloading " + repository.save(
                    new Watch("002", "Michael Kors", new BigDecimal(200),
                            new Discount(2, new BigDecimal(120)))));
            log.info("Preloading " + repository.save(
                    new Watch("003", "Swatch", new BigDecimal(50), null)));
            log.info("Preloading " + repository.save(
                    new Watch("004", "Casio", new BigDecimal(30), null)));
        };
    }
}
