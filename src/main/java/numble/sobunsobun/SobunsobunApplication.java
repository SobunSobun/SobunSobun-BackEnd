package numble.sobunsobun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = ContextStackAutoConfiguration.class)
public class SobunsobunApplication {

	public static void main(String[] args) {
		SpringApplication.run(SobunsobunApplication.class, args);
	}

}
