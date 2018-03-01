package com.wechat.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to A 5 Backend.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final Wx wx= new ApplicationProperties.Wx();

    public Wx getWx() {
        return wx;
    }

    public static class Wx{
        public Wx(){
        }
        private String appId;
        private String appSecret;
        private String oauthUri;

        public String getOauthUri() {
            return oauthUri;
        }

        public void setOauthUri(String oauthUri) {
            this.oauthUri = oauthUri;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

    }
}
