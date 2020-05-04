package site.yl.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude= {JooqAutoConfiguration.class})
@ServletComponentScan(basePackages = {"site.yl.template"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}