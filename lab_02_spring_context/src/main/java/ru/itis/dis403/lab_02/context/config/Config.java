package ru.itis.dis403.lab_02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.itis.dis403.lab_02.components.MarketService;
import ru.itis.dis403.lab_02.model.Market;

@Configuration
@ComponentScan("ru.itis.dis403.lab_02")
public class Config {
    @Bean
    public MarketService marketService() {
        Market market = new Market();
        return new MarketService(market);
    }
}
