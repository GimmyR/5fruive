package mg.fruive.record;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartEntryForm(
		@NotNull(message = "Product ID is missing")
		Integer productId, 
		
		@NotNull(message = "Amount is missing")
		@Positive(message = "Amount value should be positive")
		Float amount
) {}
