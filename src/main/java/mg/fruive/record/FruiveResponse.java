package mg.fruive.record;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FruiveResponse {
	
	public int status;
	public String message;
	public Object data;

}
