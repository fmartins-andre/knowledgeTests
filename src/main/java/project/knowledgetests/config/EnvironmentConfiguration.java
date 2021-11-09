package project.knowledgetests.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class EnvironmentConfiguration implements EnvironmentAware {

    private static Environment environment;

    public static String get(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    @Override
    public void setEnvironment(Environment environment) {
        EnvironmentConfiguration.environment = environment;
    }
}
