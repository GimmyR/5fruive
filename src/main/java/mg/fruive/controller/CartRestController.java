package mg.fruive.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import mg.fruive.entity.FruiveResponse;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;
import mg.fruive.service.CartService;

@RestController
@AllArgsConstructor
public class CartRestController {
	
	private CartService cartService;
	
	@GetMapping("/api/cart")
	public FruiveResponse getCart() {
		
		FruiveResponse response = new FruiveResponse(200, null, null);
		response.data = cartService.getCart().size();
		return response;
		
	}
	
	@PostMapping("/api/cart/add")
	public FruiveResponse addToCart(@RequestParam(required = false) Integer productId, @RequestParam(required = false) Float amount) {
		
		FruiveResponse response = new FruiveResponse(201, "Product saved in cart !", null);
		
		try {
			
			response.data = cartService.addToCart(productId, amount);
			
		} catch (NullPointerException e) {
			
			response = new FruiveResponse(400, e.getMessage(), null);
			
		} catch (NotFoundException e) {
			
			response = new FruiveResponse(404, e.getMessage(), null);
			
		} catch (OutOfStockException e) {
			
			response = new FruiveResponse(400, e.getMessage(), null);
			
		} return response;
		
	}

}
