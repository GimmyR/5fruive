package mg.fruive.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import mg.fruive.exception.FileNotFoundException;

@Service
public class ImageService {
	
	private final Path uploadsDir = Paths.get("images");
	
	public void createDirectory() throws IOException {
		
		if(Files.notExists(uploadsDir))
			Files.createDirectory(uploadsDir);
		
	}
	
	public Resource get(String filename) throws MalformedURLException, FileNotFoundException {
		
		Path file = uploadsDir.resolve(filename);
		Resource resource = new UrlResource(file.toUri());
		
		if(!resource.exists() || !resource.isReadable())
			throw new FileNotFoundException();
		
		return resource;
		
	}

}
