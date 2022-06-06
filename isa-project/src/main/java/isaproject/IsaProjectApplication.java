package isaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IsaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsaProjectApplication.class, args);
	}

}
