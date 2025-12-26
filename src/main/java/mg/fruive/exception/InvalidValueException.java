package mg.fruive.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Getter
@NoArgsConstructor
public class InvalidValueException extends Exception {
	
	public InvalidValueException(String message) {
		
		super(message);
		
	}

}
