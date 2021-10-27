package project.knowledgetests.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.TimeZone;

@Configuration
public class LocaleConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println("# APPLICATION: Timezone configured to UTC");
        System.out.println("# APPLICATION: UTC Date and Time is " + OffsetDateTime.now());
    }
}
