package mg.fruive.record;

import jakarta.validation.constraints.NotNull;

public record CartEntryForm(
		@NotNull(message = "Product ID is missing")
		Integer productId, 
		
		@NotNull(message = "Amount is missing")
		Float amount
) {}
