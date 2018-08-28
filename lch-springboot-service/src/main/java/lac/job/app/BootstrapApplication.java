package lac.job.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import lac.job.util.DmsSpringUtil;

@SpringBootApplication
@ComponentScan(basePackages = { "lac", "com" })
@MapperScan("lac.job.mapper")
@EnableAutoConfiguration
@Import(DmsSpringUtil.class)
public class BootstrapApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BootstrapApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BootstrapApplication.class, args);
	}

}