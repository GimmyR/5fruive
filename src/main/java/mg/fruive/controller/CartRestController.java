package mg.fruive.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import mg.fruive.domain.CartEntry;
import mg.fruive.exception.InvalidValueException;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;
import mg.fruive.record.FruiveResponse;
import mg.fruive.service.CartService;

@RestController
@AllArgsConstructor
public class CartRestController {
	
	private CartService cartService;
	
	@GetMapping("/api/cart-size")
	public ResponseEntity<FruiveResponse> getCartSize() {
		
		FruiveResponse response = new FruiveResponse(null, cartService.getCartSize());
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping("/api/cart/add")
	public ResponseEntity<FruiveResponse> addToCart(@RequestParam(required = false) Integer productId, @RequestParam(required = false) Float amount) {
		
		int status = 201;
		FruiveResponse response = null;
		
		try {
			
			response = new FruiveResponse("Product saved in cart !", cartService.addToCart(productId, amount));
			
		} catch (NullPointerException | OutOfStockException | InvalidValueException e) {
			
			status = 400;
			response = new FruiveResponse(e.getMessage(), null);
			
		} catch (NotFoundException e) {
			
			status = 404;
			response = new FruiveResponse(e.getMessage(), null);
			
		} return ResponseEntity.status(status).body(response);
		
	}
	
	@PostMapping("/api/cart/remove/{productId}")
	public ResponseEntity<FruiveResponse> removeProduct(@PathVariable Integer productId) {
		
		int status = 201;
		FruiveResponse response = null;
		
		try {
			
			cartService.removeProduct(productId);
			response = new FruiveResponse("Product is successfully removed from cart !", null);
			
		} catch (NotFoundException e) {
			
			status = 404;
			response = new FruiveResponse(e.getMessage(), null);
			
		} return ResponseEntity.status(status).body(response);
		
	}
	
	@PostMapping("/api/cart/save")
	public ResponseEntity<FruiveResponse> saveCart(@RequestBody List<CartEntry> entries) {
		
		int status = 201;
		FruiveResponse response = null;
		
		try {
			
			cartService.saveCart(entries);
			response = new FruiveResponse("Cart is successfully saved !", null);
			
		} catch (NullPointerException | OutOfStockException | InvalidValueException e) {
			
			status = 400;
			response = new FruiveResponse(e.getMessage(), null);
			
		} catch (NotFoundException e) {
			
			status = 404;
			response = new FruiveResponse(e.getMessage(), null);
			
		} return ResponseEntity.status(status).body(response);
		
	}

}
