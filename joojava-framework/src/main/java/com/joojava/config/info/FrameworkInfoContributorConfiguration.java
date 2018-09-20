package com.joojava.config.info;

import org.springframework.boot.actuate.autoconfigure.info.ConditionalOnEnabledInfoContributor;
import org.springframework.boot.actuate.autoconfigure.info.InfoContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author leegive
 *
 */
@Configuration
@AutoConfigureAfter(InfoContributorAutoConfiguration.class)
public class FrameworkInfoContributorConfiguration {

    @Bean
    @ConditionalOnEnabledInfoContributor("management.info.active-profiles.enabled")
    public ActiveProfilesInfoContributor activeProfilesInfoContributor(ConfigurableEnvironment environment) {
        return new ActiveProfilesInfoContributor(environment);
    }

    @Bean
    @ConditionalOnEnabledInfoContributor("management.info.mail-enabled.enabled")
    public MailEnabledInfoContributor mailEnabledInfoContributor() {
        return new MailEnabledInfoContributor();
    }

}
