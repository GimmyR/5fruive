package mg.fruive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class FruiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(FruiveApplication.class, args);
	}

}
