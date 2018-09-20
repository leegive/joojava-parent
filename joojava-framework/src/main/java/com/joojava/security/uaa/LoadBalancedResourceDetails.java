package com.joojava.security.uaa;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author leegive
 */
@Slf4j
@ConditionalOnMissingBean
public class LoadBalancedResourceDetails extends ClientCredentialsResourceDetails {

    public static final String EXCEPTION_MESSAGE = "Returning an invalid URI: {}";

    @Getter @Setter
    private String tokenServiceId;

    private LoadBalancerClient loadBalancerClient;

    public LoadBalancedResourceDetails(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    @Override
    public String getAccessTokenUri() {
        if (loadBalancerClient != null && tokenServiceId != null && !tokenServiceId.isEmpty()) {
            try {
                return loadBalancerClient.reconstructURI(loadBalancerClient.choose(tokenServiceId), new URI(super.getAccessTokenUri())).toString();
            } catch (URISyntaxException e) {
                log.error(EXCEPTION_MESSAGE, e.getMessage());
                return super.getAccessTokenUri();
            }
        } else {
            return super.getAccessTokenUri();
        }
    }
}
