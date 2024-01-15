package space.dakuuwu.dakwebBK.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cors")
public record CORSProperties(String allowedOrigins, String allowedHeaders, String allowedMethods) {
}
