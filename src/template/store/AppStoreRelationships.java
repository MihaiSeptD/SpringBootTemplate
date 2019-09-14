package template.store;


import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan("template.store")
@EnableAutoConfiguration
public class AppStoreRelationships{

	private final static Logger LOG = LoggerFactory.getLogger(AppStoreRelationships.class);
	
	private final static String PORT = "8089";

	public static void main(String ... args) {
		LOG.info("start application for CRUD store");
		SpringApplication  app = new SpringApplication(AppStoreRelationships.class);
		
		app.setDefaultProperties(Collections
		          .singletonMap("server.port", PORT));
		app.run(args);
	}
}
