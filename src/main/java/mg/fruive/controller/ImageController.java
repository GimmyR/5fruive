package mg.fruive.controller;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import mg.fruive.exception.FileNotFoundException;
import mg.fruive.service.ImageService;

@Controller
@AllArgsConstructor
public class ImageController {
	
	private ImageService imageService;
	
	@GetMapping("/images/{filename:.+}")
	public ResponseEntity<Resource> get(@PathVariable String filename) {
		
		ResponseEntity<Resource> result = null;
		
		try {
			
			Resource resource = imageService.get(filename);
			result = ResponseEntity
						.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
						.body(resource);
			
		} catch (MalformedURLException | FileNotFoundException e) {

			e.printStackTrace();
			
		} return result;
		
	}

}
