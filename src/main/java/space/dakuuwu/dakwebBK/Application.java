package space.dakuuwu.dakwebBK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import space.dakuuwu.dakwebBK.configs.RsaKeyProperties;
import space.dakuuwu.dakwebBK.configs.UserProperties;

@SpringBootApplication
@EnableMongoRepositories
@EnableWebSecurity
@EnableConfigurationProperties({RsaKeyProperties.class, UserProperties.class})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
