package mg.fruive.record;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AmountForm(
		@NotNull(message = "Amount value is missing")
		@Positive(message = "Amount value should be positive")
		Float amount
) {}
