package mg.fruive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.AllArgsConstructor;
import mg.fruive.service.ImageService;

@SpringBootApplication
@AllArgsConstructor
public class FruiveApplication implements CommandLineRunner {
	
	private ImageService imageService;

	public static void main(String[] args) {
		SpringApplication.run(FruiveApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		imageService.createDirectory();
	}

}
