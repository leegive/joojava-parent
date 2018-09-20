package com.joojava.config.info;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.List;

/**
 * @author leegive
 * 获取开启的Profiles
 */
public class ActiveProfilesInfoContributor implements InfoContributor {

    private static final String ACTIVE_PROFILES = "activeProfiles";
    private final List<String> profiles;

    public ActiveProfilesInfoContributor(ConfigurableEnvironment environment) {
        this.profiles = Arrays.asList(environment.getActiveProfiles());
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail(ACTIVE_PROFILES, this.profiles);
    }
}
