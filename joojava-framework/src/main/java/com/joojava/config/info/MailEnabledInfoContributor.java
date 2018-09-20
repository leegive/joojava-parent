package com.joojava.config.info;

import com.joojava.config.FrameworkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

/**
 * @author leegive
 *
 * Mail开启信息
 */
public class MailEnabledInfoContributor implements InfoContributor {

    private static final String MAIL_ENABLED = "mailEnabled";

    @Autowired
    private FrameworkProperties frameworkProperties;


    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail(MAIL_ENABLED, frameworkProperties.getMail().isEnabled());
    }
}
