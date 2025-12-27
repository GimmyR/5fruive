package mg.fruive.record;

import jakarta.validation.constraints.NotBlank;

public record CardForm(
		@NotBlank(message = "Card code is missing")
		String code
) {}
