package mg.fruive.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Getter
@NoArgsConstructor
public class InvalidValueException extends Exception {
	
	private String parameter;
	
	public InvalidValueException(String parameter, String message) {
		
		super(message);
		this.parameter = parameter;
		
	}

}
