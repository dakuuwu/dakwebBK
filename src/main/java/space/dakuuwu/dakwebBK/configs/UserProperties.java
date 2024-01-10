package space.dakuuwu.dakwebBK.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "usr")
public record UserProperties(String username, String password) {
}

