package mg.fruive.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import mg.fruive.entity.CartEntry;
import mg.fruive.entity.FruiveResponse;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;
import mg.fruive.service.CartService;

@RestController
@AllArgsConstructor
public class CartRestController {
	
	private CartService cartService;
	
	@GetMapping("/api/cart-size")
	public FruiveResponse getCartSize() {
		
		FruiveResponse response = new FruiveResponse(200, null, 0);
		Map<Integer, Float> cart = cartService.getCart();
		
		if(cart != null)
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
	
	@PostMapping("/api/cart/remove/{productId}")
	public FruiveResponse removeProduct(@PathVariable Integer productId) {
		
		FruiveResponse response = new FruiveResponse(201, "Product is successfully removed from cart !", null);
		
		try {
			
			cartService.removeProduct(productId);
			
		} catch (NotFoundException e) {
			
			response = new FruiveResponse(404, e.getMessage(), null);
			
		} return response;
		
	}
	
	@PostMapping("/api/cart/save")
	public FruiveResponse saveCart(@RequestBody List<CartEntry> entries) {
		
		FruiveResponse response = new FruiveResponse(201, "Cart is successfully saved !", null);
		
		try {
			
			cartService.saveCart(entries);
			
		} catch (NullPointerException e) {
			
			response = new FruiveResponse(400, e.getMessage(), null);
			
		} catch (NotFoundException e) {
			
			response = new FruiveResponse(404, e.getMessage(), null);
			
		} catch (OutOfStockException e) {
			
			response = new FruiveResponse(400, e.getMessage(), null);
			
		} return response;
		
	}

}
